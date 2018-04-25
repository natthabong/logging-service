DROP TABLE tbl_batch_tracking_items;
CREATE TABLE tbl_batch_tracking_items (
	batch_tracking_item_id bigint NOT NULL AUTO_INCREMENT,
	batch_tracking_id varchar(60) NOT NULL,
	action varchar(255) NOT NULL,
	action_time DATETIME(3) NOT NULL,
	reference_no varchar(255) NOT NULL,
	transaction_no varchar(50) NOT NULL,
	is_completed bit NOT NULL,
 CONSTRAINT PK_BatchTrackingItem PRIMARY KEY (batch_tracking_item_id),
 CONSTRAINT FK_BatchTrackingItem FOREIGN KEY(batch_tracking_id) REFERENCES  tbl_batch_tracking  (id)
);