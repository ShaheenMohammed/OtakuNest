/*
    OtakuNest Schema
    Author: Shaheen Mohammed
*/   

CREATE OR REPLACE FUNCTION generate_uuid_v7()
RETURNS uuid
AS $$
DECLARE
    v_time timestamp with time zone := clock_timestamp();
    v_unix_ts_ms bigint;
    v_uuid uuid := gen_random_uuid();
    v_uuid_hex text;
BEGIN
    v_unix_ts_ms := (extract(epoch from v_time) * 1000)::bigint;
    v_uuid_hex := replace(v_uuid::text, '-', '');
    RETURN (
        lpad(to_hex(v_unix_ts_ms), 12, '0') ||
        '7' ||
        substr(v_uuid_hex, 14, 3) ||
        substr(v_uuid_hex, 17, 4) ||
        substr(v_uuid_hex, 21, 12)
    )::uuid;
END
$$
LANGUAGE plpgsql
VOLATILE;

-- ENUMS
CREATE TYPE media_type AS ENUM ('MOVIE', 'ANIME', 'DEMO_ANIME', 'MANGA', 'DEMO_MANGA', 'MANHWA', 'WESTERN', '3D', 'CG', 'IMAGESET');
CREATE TYPE release_status AS ENUM ('RELEASING', 'FINISHED');
CREATE TYPE season_type AS ENUM ('WINTER', 'SPRING', 'SUMMER', 'FALL');
CREATE TYPE entity_type AS ENUM ('EPISODE', 'CHAPTER');
CREATE TYPE thumbnail_usage AS ENUM ('POSTER', 'BANNER', 'GALLERY', 'AVATAR', 'SEEKBAR');
CREATE TYPE watching_status AS ENUM ('WATCHING', 'READING', 'COMPLETED', 'DROPPED');
CREATE TYPE cast_role_type AS ENUM ('MAIN', 'SUPPORTING');
CREATE TYPE scanner_job_status AS ENUM ('PENDING', 'RUNNING', 'COMPLETED', 'FAILED');
CREATE TYPE source_api_type AS ENUM ('ANILIST', 'ANIDB', 'MAL', 'TMDB');
CREATE TYPE relation_type AS ENUM ('ADAPTATION', 'PREQUEL', 'SEQUEL', 'SIDE_STORY', 'SPIN_OFF', 'ALTERNATIVE', 'OTHER');
CREATE TYPE user_role AS ENUM ('ADMIN', 'DEMO');

-- CORE ENTITIES

-- User Account Credentials.
CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT generate_uuid_v7(),
    username VARCHAR(100) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password_hash TEXT NOT NULL,
    role user_role DEFAULT 'DEMO' NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL
);

-- Ensures there can only be one ADMIN user in the entire system
CREATE UNIQUE INDEX idx_one_admin ON users(role) WHERE role = 'ADMIN';

-- UI preferences and third-party metadata site keys.
CREATE TABLE user_settings (
    user_id UUID PRIMARY KEY REFERENCES users(id) ON DELETE CASCADE,
    theme VARCHAR(20) DEFAULT 'dark' NOT NULL,
    auto_play BOOLEAN DEFAULT TRUE NOT NULL,
    external_api_keys JSONB,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL
);

-- Metadata for every media item (Movie, Anime, Manga, Demo_Anime, Demo_Manga)
CREATE TABLE media_items (
    id UUID PRIMARY KEY DEFAULT generate_uuid_v7(),
    media_item_type media_type NOT NULL,
    title VARCHAR(120) NOT NULL,
    synopsis TEXT,
    media_item_status release_status NOT NULL DEFAULT 'RELEASING',
    release_date DATE,
    media_item_year INTEGER,
    release_season season_type,
    studio_name VARCHAR(70),
    artist VARCHAR(70),
    groups VARCHAR(70),
    cover_image_url TEXT,
    banner_image_url TEXT,
    popularity_score DECIMAL(5,2) DEFAULT 0.0 NOT NULL,
    total_episodes_count INTEGER DEFAULT 0 NOT NULL CHECK (total_episodes_count >= 0),
    total_chapters_count INTEGER DEFAULT 0 NOT NULL CHECK (total_chapters_count >= 0),
    external_ids JSONB,
    search_vector TSVECTOR,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT chk_media_item_fields CHECK (
        NOT (media_item_type IN ('ANIME', 'DEMO_ANIME', 'MOVIE') AND artist IS NOT NULL) AND
        NOT (media_item_type IN ('MANGA', 'DEMO_MANGA', 'MANHWA', 'WESTERN', '3D', 'CG', 'IMAGESET') AND studio_name IS NOT NULL)
    )
);

-- Details for individual video items.
CREATE TABLE episodes (
    id UUID PRIMARY KEY DEFAULT generate_uuid_v7(),
    media_item_id UUID NOT NULL REFERENCES media_items(id) ON DELETE CASCADE,
    episode_number DECIMAL(6,2) NOT NULL,
    title VARCHAR(120),
    synopsis TEXT,
    air_date TIMESTAMP WITH TIME ZONE,
    duration_seconds INTEGER CHECK (duration_seconds >= 0),
    is_filler BOOLEAN DEFAULT FALSE NOT NULL,
    external_ids JSONB,
    tags TEXT[],
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    UNIQUE(media_item_id, episode_number)
);

-- Details for individual reading items.
CREATE TABLE chapters (
    id UUID PRIMARY KEY DEFAULT generate_uuid_v7(),
    media_item_id UUID NOT NULL REFERENCES media_items(id) ON DELETE CASCADE,
    chapter_number DECIMAL(6,2) NOT NULL,
    volume_number INTEGER,
    title VARCHAR(120),
    release_date TIMESTAMP WITH TIME ZONE,
    tags TEXT[],
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    UNIQUE(media_item_id, chapter_number)
);

-- Connects an anime episode to a range of manga chapters.
CREATE TABLE episode_chapter_mappings (
    id UUID PRIMARY KEY DEFAULT generate_uuid_v7(),
    episode_id UUID NOT NULL REFERENCES episodes(id) ON DELETE CASCADE,
    manga_media_item_id UUID NOT NULL REFERENCES media_items(id) ON DELETE CASCADE,
    start_chapter_number DECIMAL(6,2) NOT NULL,
    end_chapter_number DECIMAL(6,2) NOT NULL,
    UNIQUE(episode_id, manga_media_item_id)
);

-- Connects an Anime to its Manga source material, or Sequels/Prequels.
CREATE TABLE media_relations (
    id UUID PRIMARY KEY DEFAULT generate_uuid_v7(),
    source_media_item_id UUID NOT NULL REFERENCES media_items(id) ON DELETE CASCADE,
    target_media_item_id UUID NOT NULL REFERENCES media_items(id) ON DELETE CASCADE,
    relation relation_type NOT NULL,
    UNIQUE(source_media_item_id, target_media_item_id, relation)
);

-- FILTERING ENTITIES

-- Genre classifications like Action, Romance, or Horror.
CREATE TABLE genres (
    id UUID PRIMARY KEY DEFAULT generate_uuid_v7(),
    genre_name VARCHAR(120) UNIQUE NOT NULL
);

-- Connects multiple genres to a media item.
CREATE TABLE media_item_genres (
    id UUID PRIMARY KEY DEFAULT generate_uuid_v7(),
    media_item_id UUID NOT NULL REFERENCES media_items(id) ON DELETE CASCADE,
    genre_id UUID NOT NULL REFERENCES genres(id) ON DELETE CASCADE,
    UNIQUE(media_item_id, genre_id)
);

-- Specific descriptors like 'Gore', 'Isekai', or 'School Life'.
CREATE TABLE tags (
    id UUID PRIMARY KEY DEFAULT generate_uuid_v7(),
    tag_name VARCHAR(100) UNIQUE NOT NULL,
    synopsis TEXT
);

-- Connects multiple tags to a media item.
CREATE TABLE media_item_tags (
    id UUID PRIMARY KEY DEFAULT generate_uuid_v7(),
    media_item_id UUID NOT NULL REFERENCES media_items(id) ON DELETE CASCADE,
    tag_id UUID NOT NULL REFERENCES tags(id) ON DELETE CASCADE,
    rank INTEGER DEFAULT 0 NOT NULL CHECK (rank >= 0 AND rank <= 100),
    UNIQUE(media_item_id, tag_id)
);

-- VOICE ACTING AND CHARACTER BIO ENTITIES

-- Stores voice actor information
CREATE TABLE voice_actors (
    id UUID PRIMARY KEY DEFAULT generate_uuid_v7(),
    voice_actors_name VARCHAR(70) UNIQUE NOT NULL,
    external_ids JSONB
);

-- Stores character information
CREATE TABLE characters (
    id UUID PRIMARY KEY DEFAULT generate_uuid_v7(),
    character_name VARCHAR(70) UNIQUE NOT NULL,
    synopsis TEXT,
    image_url TEXT,
    external_ids JSONB
);

-- Connects voice actors, characters, and the media items they appear in.
CREATE TABLE media_item_cast (
    id UUID PRIMARY KEY DEFAULT generate_uuid_v7(),
    media_item_id UUID NOT NULL REFERENCES media_items(id) ON DELETE CASCADE,
    voice_actor_id UUID REFERENCES voice_actors(id) ON DELETE CASCADE,
    character_id UUID NOT NULL REFERENCES characters(id) ON DELETE CASCADE,
    role_type cast_role_type DEFAULT 'MAIN' NOT NULL
);

-- FILE MANAGEMENT

-- Table to keep track of media folders
CREATE TABLE library_folders (
    id UUID PRIMARY KEY DEFAULT generate_uuid_v7(),
    folder_path TEXT UNIQUE NOT NULL,
    media_folder_type media_type NOT NULL,
    is_enabled BOOLEAN DEFAULT TRUE NOT NULL
);

-- Stores technical specs and absolute paths for media files.
CREATE TABLE media_files (
    id UUID PRIMARY KEY DEFAULT generate_uuid_v7(),
    type_of_media entity_type NOT NULL,
    episode_id UUID REFERENCES episodes(id) ON DELETE CASCADE,
    chapter_id UUID REFERENCES chapters(id) ON DELETE CASCADE,
    CONSTRAINT chk_media_file_parent CHECK (
        (type_of_media = 'EPISODE' AND episode_id IS NOT NULL AND chapter_id IS NULL) OR 
        (type_of_media = 'CHAPTER' AND chapter_id IS NOT NULL AND episode_id IS NULL)
    ),
    file_path TEXT UNIQUE NOT NULL,
    file_name VARCHAR(255) NOT NULL,
    file_size_bytes BIGINT CHECK (file_size_bytes >= 0),
    file_hash VARCHAR(255),
    media_file_checksum VARCHAR(255),
    video_codec VARCHAR(50),
    resolution_width INTEGER,
    resolution_height INTEGER,
    frame_rate DECIMAL(6,3),
    subtitle_path TEXT,
    order_number INTEGER,
    is_available BOOLEAN DEFAULT TRUE NOT NULL,
    is_archived BOOLEAN DEFAULT FALSE NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL
);

-- Stores thumbnails including posters, banners, and Netflix-style seekbar previews.
CREATE TABLE thumbnails (
    id UUID PRIMARY KEY DEFAULT generate_uuid_v7(),
    media_item_id UUID REFERENCES media_items(id) ON DELETE CASCADE,
    episode_id UUID REFERENCES episodes(id) ON DELETE CASCADE,
    chapter_id UUID REFERENCES chapters(id) ON DELETE CASCADE,
    usage_type thumbnail_usage NOT NULL,
    display_order INTEGER DEFAULT 0 NOT NULL,
    timestamp_seconds DECIMAL(6,2),
    image_path TEXT NOT NULL,
    width INTEGER,
    height INTEGER,
    is_generated BOOLEAN DEFAULT FALSE NOT NULL
);

-- USER PROGRESS & SYSTEM INFO

-- Logs user watch position, rating, and status for each media item.
CREATE TABLE user_progress (
    id UUID PRIMARY KEY DEFAULT generate_uuid_v7(),
    user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    media_item_id UUID NOT NULL REFERENCES media_items(id) ON DELETE CASCADE,
    last_episode_id UUID REFERENCES episodes(id) ON DELETE SET NULL,
    last_chapter_id UUID REFERENCES chapters(id) ON DELETE SET NULL,
    watched_seconds INTEGER DEFAULT 0 NOT NULL CHECK (watched_seconds >= 0),
    user_rating INTEGER CHECK (user_rating >= 1 AND user_rating <= 10),
    media_item_status watching_status NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(user_id, media_item_id)
);

-- Audit log for library scans to monitor progress and track file errors.
CREATE TABLE scanner_jobs (
    id UUID PRIMARY KEY DEFAULT generate_uuid_v7(),
    job_status scanner_job_status DEFAULT 'PENDING' NOT NULL,
    heartbeat TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    error_log TEXT
);

-- Local stash of raw JSON to power offline metadata lookup.
CREATE TABLE external_metadata_cache (
    id UUID PRIMARY KEY DEFAULT generate_uuid_v7(),
    external_id VARCHAR(255) NOT NULL,
    source_api source_api_type NOT NULL,
    raw_json JSONB,
    UNIQUE(external_id, source_api)
);

-- PERFORMANCE INDEXES

-- Full-text queries for media item names using the search_vector.
CREATE INDEX idx_media_items_search ON media_items USING GIN(search_vector);

-- Enables fast lookup for media relations (Source -> Target is covered by UNIQUE constraint index, this covers Target -> Source)
CREATE INDEX idx_media_relations_target ON media_relations(target_media_item_id);

-- Enables fast lookup for Episode -> Manga Chapter (e.g. Which chapter does episode 3 adapt?)
-- The reverse lookup (Manga Chapter -> Episode) is covered by the UNIQUE(episode_id, manga_media_item_id) index.
CREATE INDEX idx_episode_chapter_mappings_manga ON episode_chapter_mappings(manga_media_item_id, start_chapter_number, end_chapter_number);

-- Ensures you can find a specific type of thumbnail (like a 'POSTER' or 'SEEKBAR') instantly.
CREATE INDEX idx_thumbnails_media_usage ON thumbnails(media_item_id, usage_type);
CREATE INDEX idx_thumbnails_episode_usage ON thumbnails(episode_id, usage_type);
CREATE INDEX idx_thumbnails_chapter_usage ON thumbnails(chapter_id, usage_type);

-- Identifies files by SHA-256 for library integrity audits.
CREATE INDEX idx_media_files_checksum ON media_files(media_file_checksum);

-- Retrieves active watch/read list based on user and watch status.
CREATE INDEX idx_user_progress_active ON user_progress(user_id, media_item_status);

-- Instant lookup of the entire cast and crew for any specific media item.
CREATE INDEX idx_cast_media ON media_item_cast(media_item_id);

-- Reverse lookups for voice actors and characters for fast profile loading.
CREATE INDEX idx_cast_voice_actor ON media_item_cast(voice_actor_id);
CREATE INDEX idx_cast_character ON media_item_cast(character_id);

-- Groups episodes by series for fast loading of anime detail pages.
CREATE INDEX idx_episodes_media_item ON episodes(media_item_id);

-- Groups millions of chapters by series for sub-millisecond retrieval of manga contents.
CREATE INDEX idx_chapters_media_item ON chapters(media_item_id);

-- Instantly finds the physical NAS file path when you click to play or read an item.
CREATE INDEX idx_media_files_episode ON media_files(episode_id);
CREATE INDEX idx_media_files_chapter ON media_files(chapter_id);

-- Allows smooth, high-speed sorting and filtering of the entire library by release year.
CREATE INDEX idx_media_items_year ON media_items(media_item_year DESC);

-- Optimizes loading main dashboards (e.g., "Latest Anime", "Latest Manga").
CREATE INDEX idx_media_items_type_date ON media_items(media_item_type, release_date DESC);

-- Critical for heavy genre filtering (avoids slow sequential scans on joined tables).
CREATE INDEX idx_media_item_genres_genre_id ON media_item_genres(genre_id);

-- Critical for heavy tag filtering.
CREATE INDEX idx_media_item_tags_tag_id ON media_item_tags(tag_id);
