CREATE TABLE tbl_batch_tracking (
  id VARCHAR(60) NOT NULL,
  reference_id VARCHAR(255) NOT NULL,
  process_no VARCHAR(40) NOT NULL,
  action VARCHAR(255) NOT NULL,
  action_time DATETIME NOT NULL,
  is_completed BIT NOT NULL,
  node VARCHAR(255) NOT NULL,
  ip_address VARCHAR(255) NULL,
  CONSTRAINT PK_BatchTracking PRIMARY KEY(id)
)