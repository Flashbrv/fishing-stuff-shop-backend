CREATE TABLE `fs_confirmation_tokens` (
  `id` bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `token` varchar(255) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `expires_at` datetime(6) NOT NULL,
  `confirmed_at` datetime(6) DEFAULT NULL,
  `user_id` bigint NOT NULL,
  CONSTRAINT `FK_CONFIRMATION_TOKEN_TO_USER` FOREIGN KEY (`user_id`) REFERENCES `fs_users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;