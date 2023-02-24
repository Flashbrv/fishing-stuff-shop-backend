--
-- Dumping data for table `fs_buckets`
--

INSERT INTO `fs_buckets` (`id`, `created_by`, `created_date`, `deleted`, `last_modified_by`, `last_modified_date`, `version`) VALUES
(1, NULL, '2023-02-24 10:41:32.952000', b'0', NULL, '2023-02-24 10:41:32.952000', 0),
(2, NULL, '2023-02-24 10:41:33.056000', b'0', NULL, '2023-02-24 10:41:33.056000', 0),
(3, NULL, '2023-02-24 10:41:33.138000', b'0', NULL, '2023-02-24 10:41:33.138000', 0),
(4, NULL, '2023-02-24 10:41:33.208000', b'0', NULL, '2023-02-24 10:41:33.208000', 0);
-- --------------------------------------------------------

--
-- Dumping data for table `fs_roles`
--
INSERT INTO `fs_roles` (`id`, `created_by`, `created_date`, `deleted`, `last_modified_by`, `last_modified_date`, `version`, `name`) VALUES
(1, NULL, '2023-02-24 10:41:32.781000', b'0', NULL, '2023-02-24 10:41:32.781000', 0, 'ROLE_USER'),
(2, NULL, '2023-02-24 10:41:32.844000', b'0', NULL, '2023-02-24 10:41:32.844000', 0, 'ROLE_MANAGER'),
(3, NULL, '2023-02-24 10:41:32.849000', b'0', NULL, '2023-02-24 10:41:32.849000', 0, 'ROLE_ADMIN'),
(4, NULL, '2023-02-24 10:41:32.854000', b'0', NULL, '2023-02-24 10:41:32.854000', 0, 'ROLE_SUPER_ADMIN');

-- --------------------------------------------------------

--
-- Dumping data for table `fs_users`
--

INSERT INTO `fs_users` (`id`, `created_by`, `created_date`, `deleted`, `last_modified_by`, `last_modified_date`, `version`, `email`, `first_name`, `last_name`, `password`, `phone_number`, `bucket_id`) VALUES
(1, NULL, '2023-02-24 10:41:32.947000', b'0', NULL, '2023-02-24 10:41:33.411000', 2, 'john@mail.ua', 'John', 'Travolta', '$2a$10$3QJ/iSJQThUDZ89sC6ySQ.7s0iU1tYA41k5fv4mKibBKHgXV1hGRC', '380991291320', 1),
(2, NULL, '2023-02-24 10:41:33.056000', b'0', NULL, '2023-02-24 10:41:33.424000', 1, 'will@mail.ua', 'Will', 'Smith', '$2a$10$1N4eDhA2Z4O/LpOTRw.SD.YQR89PEu5ZF6r5/Y4NG6lfx2qGQhzoi', '380991291321', 2),
(3, NULL, '2023-02-24 10:41:33.138000', b'0', NULL, '2023-02-24 10:41:33.438000', 1, 'jim@mail.ua', 'Jim', 'Carry', '$2a$10$IoDenKKXzglMCza9.RE6KuDxRAbovRJ1QESVsjHCdQlF0qK3w4UjK', '380991291322', 3),
(4, NULL, '2023-02-24 10:41:33.207000', b'0', NULL, '2023-02-24 10:41:33.484000', 3, 'arnold@mail.ua', 'Arnold', 'Schwarzenegger', '$2a$10$DR78yX/szIdYDBMwycH19.APURbiLJZEu7tD4w5VccHejB0.CTB1.', '380991291323', 4);

-- --------------------------------------------------------

--
-- Dumping data for table `fs_users_roles`
--

INSERT INTO `fs_users_roles` (`user_id`, `role_id`) VALUES
(1, 1),
(4, 1),
(1, 2),
(2, 2),
(3, 3),
(4, 3),
(4, 4);
