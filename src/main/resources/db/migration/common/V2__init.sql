
CREATE TABLE tbl_batch_tracking_items (
  batch_tracking_item_id BIGINT NOT NULL,
  batch_tracking_id VARCHAR(60) NOT NULL,
  action VARCHAR(255) NOT NULL,
  action_time DATETIME NOT NULL,
  reference_no VARCHAR(255) NOT NULL,
  transaction_no VARCHAR(50) NOT NULL,
  is_completed BIT NOT NULL,
  CONSTRAINT PK_BatchTrackingItem PRIMARY KEY(batch_tracking_item_id),
  CONSTRAINT FK_BatchTrackingItem FOREIGN KEY(batch_tracking_id) REFERENCES tbl_batch_tracking(id)
);