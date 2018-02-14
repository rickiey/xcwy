CREATE TABLE `businessservice_table` (
  `id` varchar(9) COLLATE utf8mb4_unicode_ci NOT NULL,
  `tyre` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `unlocking` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `water` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `electricity` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `gasoline` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `trailer` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `beauty` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT '0',
  `repair` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
