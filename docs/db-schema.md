## Data Persistence Overview

This document records the current data storage contracts for both halves of the project:

- **Backend (`backend/`)** – ASP.NET Core + EF Core targeting PostgreSQL (`techknowledgepills` database).
- **Mobile app (`android/`)** – Jetpack Compose app persisting auth state with Android DataStore (no embedded SQL DB).

---

## Backend: PostgreSQL Schema

EF Core builds the schema from the entities in `TechKnowledgePills.Core/Entities` and the configuration in `TechKnowledgePills.Infrastructure/Data/ApplicationDbContext.cs`. The diagram below summarizes tables, primary keys, and relationships.

```
Users ─┬─< StressIndicators
       ├─< HealthMetrics
       └─< UserContentInteractions >─┬─ Contents
```

### `users`
| Column | Type | Constraints / Notes |
| --- | --- | --- |
| `id` | `serial` | Primary key |
| `email` | `varchar(255)` | Required, unique index |
| `password_hash` | `text` | Required |
| `created_at` | `timestamp with time zone` | Defaults to `now()` |
| `last_login` | `timestamp with time zone` | Nullable |

### `contents`
| Column | Type | Constraints / Notes |
| --- | --- | --- |
| `id` | `serial` | Primary key |
| `title` | `varchar(255)` | Required |
| `type` | `integer` | Backed by `ContentType` enum (`ARTICLE`, `VIDEO`, `QUIZ`, etc.) |
| `body` | `text` | Markdown/HTML article body |
| `video_url` | `text` | Nullable |
| `quiz_data` | `jsonb` | Nullable; quiz definition payload |
| `created_at` | `timestamp with time zone` | Defaults to `now()` |
| `tags` | `text` | Nullable comma-separated labels |

### `stress_indicators`
| Column | Type | Constraints / Notes |
| --- | --- | --- |
| `id` | `serial` | Primary key |
| `user_id` | `int` | FK → `users.id`, cascade delete |
| `stress_level` | `integer` | Backed by `StressLevel` enum (`LOW`, `MODERATE`, `HIGH`, `CRITICAL`) |
| `timestamp` | `timestamp with time zone` | Defaults to `now()` |
| `notes` | `text` | Nullable free-form notes |

### `user_content_interactions`
| Column | Type | Constraints / Notes |
| --- | --- | --- |
| `id` | `serial` | Primary key |
| `user_id` | `int` | FK → `users.id`, cascade delete |
| `content_id` | `int` | FK → `contents.id`, cascade delete |
| `completed_at` | `timestamp with time zone` | Defaults to `now()` |
| `rating` | `int` | Nullable 1–5 star score |

### `health_metrics`
| Column | Type | Constraints / Notes |
| --- | --- | --- |
| `id` | `serial` | Primary key |
| `user_id` | `int` | FK → `users.id`, cascade delete |
| `timestamp` | `timestamp with time zone` | Defaults to `now()`; indexed with `user_id` |
| `heart_rate` | `int` | Nullable BPM |
| `steps` | `int` | Nullable daily total |
| `sleep_hours` | `double precision` | Nullable |
| `heart_rate_variability` | `int` | Nullable HRV in ms |
| `body_temperature` | `double precision` | Nullable °C |
| `device_id` | `text` | Nullable; separate index for fast lookups |
| `device_type` | `text` | Nullable descriptor |

### Additional Notes
- All FK relationships use `OnDelete(DeleteBehavior.Cascade)` so child rows are removed when a user or content record is deleted.
- `HealthMetrics` adds indexes on `device_id` and on the composite `(user_id, timestamp)` to accelerate timeline queries and deduplicate device feeds.
- The connection string lives in `TechKnowledgePills.API/appsettings.json` with host `db`, port `5432`, database `techknowledgepills`, and credentials `postgres/postgres`.

---

## Mobile App: DataStore Schema

The Android client does not embed a SQL database. Persistent data lives in an `androidx.datastore` preferences file defined in `TokenManager` (`com.techknowledgepills.data.local.TokenManager`).

| Preference Name | Key | Type | Purpose |
| --- | --- | --- | --- |
| `auth_prefs` | `auth_token` | `String` | Stores the short-lived JWT issued by the backend. |
|  | `refresh_token` | `String` | Persists the refresh token when implemented. |
|  | `user_id` | `String` (user ID serialized as text) | Lets the app quickly identify the logged-in user. |

Notes:
- DataStore writes are handled via `TokenManager.saveToken`, `saveRefreshToken`, and `saveUserId`. Reads are exposed as both `Flow` (`token`) and synchronous (`getToken()`).
- Clearing auth state calls `TokenManager.clearTokens`, which removes all three keys before redirecting the user to the login flow.
- Any future offline caching (e.g., Room or Proto DataStore) should extend this section with the new tables/messages plus migration steps.

---

## How to Keep This Doc Updated
1. When adding EF Core entities or changing `OnModelCreating`, reflect the change here.
2. For the mobile app, document any new DataStore keys, Room entities, or other local persistence layers so QA and DevOps know what data sits on-device.

