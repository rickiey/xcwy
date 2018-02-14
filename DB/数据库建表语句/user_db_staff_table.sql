CREATE TABLE `staff_table` (
  `mobile` varchar(11) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `business_id` varchar(9) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `state` int(11) DEFAULT '1',
  `order_id` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT '0',
  PRIMARY KEY (`mobile`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
