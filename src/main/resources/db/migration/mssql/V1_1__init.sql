CREATE TABLE tbl_batch_tracking_parameters (
  id VARCHAR(60) NOT NULL,
  param_key VARCHAR(255) NOT NULL,
  param_value VARCHAR(255) NOT NULL,
  CONSTRAINT PK_BatchTrackingParameters PRIMARY KEY(id, param_key),
  CONSTRAINT FK_BatchTrackingId FOREIGN KEY(id) REFERENCES tbl_batch_tracking(id)
);