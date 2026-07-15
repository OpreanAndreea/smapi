INSERT INTO users(id, username, password, first_name, last_name, email, phone)
VALUES (1, 'ajurje', '123', 'Andreea', 'Jurje', 'andreea.oprean@ibm.com', '+40740284013');

INSERT INTO jobs (title, description, start_date, end_date, deadline, created_at, updated_at)
VALUES
('Setup OAuth2 Authorization Server', 'Design and implement SSO authentication flow.', NULL, NULL, '2026-08-15 17:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Draft Cloud Migration Strategy', 'Formulate budget and resource estimates for AWS migration.', NULL, NULL, '2026-09-01 09:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Refactor Legacy Payment Service', 'Migrate deprecated payment APIs to the new Stripe library.', '2026-07-01 09:00:00', NULL, '2026-08-30 18:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Frontend UI Kit Integration', 'Replace old CSS framework with the new shared Tailwind library.', '2026-07-10 10:00:00', NULL, '2026-08-15 17:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Upgrade Spring Boot to 4.x', 'Upgrade entire microservice ecosystem to latest versions.', '2026-06-15 09:00:00', '2026-07-05 16:30:00', '2026-07-10 17:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Database Index Optimization', 'Add indexes to critical columns in user and order tables.', '2026-07-01 09:00:00', '2026-07-03 14:00:00', '2026-07-05 18:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Update API Swagger Documentation', 'Document all missing endpoint endpoints for external integrators.', '2026-06-20 09:00:00', NULL, '2026-07-10 17:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Quarterly Security Patching', 'Apply OS-level security updates to all production clusters.', '2026-07-01 08:00:00', NULL, '2026-07-12 12:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Prepare Q3 Product Demo', 'Prepare slide deck and live sandbox environments for stakeholders.', '2026-07-12 09:00:00', NULL, '2026-07-18 17:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Renew SSL Certificates', 'Update expiring letsencrypt certificates on the staging server.', '2026-07-14 13:00:00', NULL, '2026-07-16 23:59:59', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO tasks (name, description, type, priority, status, created_at, job_id)
VALUES
    ('Update POM dependencies', 'Bump Spring Boot starter versions and fix deprecations', 'OTHER', 'HIGH', 'CREATED', '2026-07-14 09:30:00', 1),
    ('Database migration verification', 'Verify PostgreSQL UUID scripts run correctly', 'OTHER', 'CRITICAL', 'CREATED', '2026-07-14 09:35:00', 1),
    ('Draft new landing page layout', 'Create mockups for the marketing page', 'OTHER', 'MEDIUM', 'CREATED', '2026-07-14 10:00:00', 2),
    ('Implement OAuth2 Login', 'Integrate Spring Security with Google and GitHub OAuth providers', 'OTHER', 'HIGH', 'IN_PROGRESS', '2026-07-14 10:15:00', 1),
    ('Refactor controller exceptions', 'Set up @ControllerAdvice for uniform API error responses', 'OTHER', 'MEDIUM', 'IN_PROGRESS', '2026-07-14 11:00:00', 2),
    ('Setup AWS S3 bucket', 'Configure CloudFront and S3 for media storage. Blocked on IAM role creation by DevOps.', 'OTHER', 'HIGH', 'BLOCKED', '2026-07-14 11:30:00', 1),
    ('Create validation annotations', 'Add custom password strength validation annotations for user DTOs', 'OTHER', 'LOW', 'IN_REVIEW', '2026-07-14 12:00:00', 2),
    ('Database schema design', 'Draft entity relationships and create initial SQL scripts', 'OTHER', 'CRITICAL', 'DONE', '2026-07-13 09:00:00', 1),
    ('Initialize git repository', 'Set up README, .gitignore, and repository branch protection rules', 'OTHER', 'MEDIUM', 'DONE', '2026-07-13 10:00:00', 2),
    ('Legacy SOAP client integration', 'Deemed unnecessary after client confirmed support for modern REST endpoints', 'OTHER', 'LOW', 'CANCELLED', '2026-07-13 14:00:00', 3);
INSERT INTO attachments (id, file_name, file_url, file_size, content_type, uploaded_at, task_id)
VALUES
    (gen_random_uuid(), 'pom_backup.xml', 'https://s3.amazonaws.com/smapi/backups/pom_backup.xml', 4096, 'application/xml', '2026-07-14 09:40:00', 1),
    (gen_random_uuid(), 'schema_draft.sql', 'https://s3.amazonaws.com/smapi/sql/schema_draft.sql', 2048, 'text/plain', '2026-07-14 09:45:00', 2);