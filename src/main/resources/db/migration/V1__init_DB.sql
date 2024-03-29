SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `fishing_stuff_shop`
--

-- --------------------------------------------------------

--
-- Table structure for table `fs_buckets`
--
CREATE TABLE `fs_buckets` (
  `id` bigint NOT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `deleted` bit(1) DEFAULT 0,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime(6) DEFAULT NULL,
  `version` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Table structure for table `fs_bucket_items`
--
CREATE TABLE `fs_bucket_items` (
  `id` bigint NOT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `deleted` bit(1) DEFAULT 0,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime(6) DEFAULT NULL,
  `version` int DEFAULT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `qty` int DEFAULT NULL,
  `bucket_id` bigint NOT NULL,
  `product_id` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `fs_categories`
--
CREATE TABLE `fs_categories` (
  `id` bigint NOT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `deleted` bit(1) DEFAULT 0,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime(6) DEFAULT NULL,
  `version` int DEFAULT NULL,
  `title` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `fs_orders`
--
CREATE TABLE `fs_orders` (
  `id` bigint NOT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `deleted` bit(1) DEFAULT 0,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime(6) DEFAULT NULL,
  `version` int DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `user_id` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `fs_order_items`
--
CREATE TABLE `fs_order_items` (
  `id` bigint NOT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `deleted` bit(1) DEFAULT 0,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime(6) DEFAULT NULL,
  `version` int DEFAULT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `qty` int DEFAULT NULL,
  `order_id` bigint NOT NULL,
  `product_id` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `fs_products`
--
CREATE TABLE `fs_products` (
  `id` bigint NOT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `deleted` bit(1) DEFAULT 0,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime(6) DEFAULT NULL,
  `version` int DEFAULT NULL,
  `description` varchar(255) NOT NULL,
  `image` varchar(255) DEFAULT NULL,
  `price` decimal(19,2) NOT NULL,
  `title` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `fs_products_categories`
--
CREATE TABLE `fs_products_categories` (
  `product_id` bigint NOT NULL,
  `category_id` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `fs_roles`
--
CREATE TABLE `fs_roles` (
  `id` bigint NOT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `deleted` bit(1) DEFAULT 0,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime(6) DEFAULT NULL,
  `version` int DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Table structure for table `fs_users`
--
CREATE TABLE `fs_users` (
  `id` bigint NOT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `deleted` bit(1) DEFAULT 0,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime(6) DEFAULT NULL,
  `version` int DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `phone_number` varchar(255) NOT NULL,
  `bucket_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Table structure for table `fs_users_roles`
--
CREATE TABLE `fs_users_roles` (
  `user_id` bigint NOT NULL,
  `role_id` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Indexes for tables
--

--
-- Indexes for table `fs_buckets`
--
ALTER TABLE `fs_buckets`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `fs_bucket_items`
--
ALTER TABLE `fs_bucket_items`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_BUCKET_ITEMS_TO_BUCKET` (`bucket_id`),
  ADD KEY `FK_BUCKET_ITEMS_TO_PRODUCT` (`product_id`);

--
-- Indexes for table `fs_categories`
--
ALTER TABLE `fs_categories`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_CATEGORIES_TITLE` (`title`);

--
-- Indexes for table `fs_orders`
--
ALTER TABLE `fs_orders`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_ORDERS_TO_USER` (`user_id`);

--
-- Indexes for table `fs_order_items`
--
ALTER TABLE `fs_order_items`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_ORDER_ITEMS_TO_ORDER` (`order_id`),
  ADD KEY `FK_ORDER_ITEMS_TO_PRODUCT` (`product_id`);

--
-- Indexes for table `fs_products`
--
ALTER TABLE `fs_products`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_PRODUCTS_TITLE` (`title`);

--
-- Indexes for table `fs_products_categories`
--
ALTER TABLE `fs_products_categories`
  ADD PRIMARY KEY (`product_id`,`category_id`),
  ADD KEY `FK_PRODUCTS_CATEGORIES_TO_CATEGORY` (`category_id`);

--
-- Indexes for table `fs_roles`
--
ALTER TABLE `fs_roles`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_ROLES_NAME` (`name`);

--
-- Indexes for table `fs_users`
--
ALTER TABLE `fs_users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_USERS_EMAIL` (`email`),
  ADD UNIQUE KEY `UK_USERS_PHONE_NUMBER` (`phone_number`),
  ADD KEY `FK_USERS_TO_BUCKET` (`bucket_id`);

--
-- Indexes for table `fs_users_roles`
--
ALTER TABLE `fs_users_roles`
  ADD PRIMARY KEY (`user_id`,`role_id`);

--
-- AUTO_INCREMENT for tables
--

--
-- AUTO_INCREMENT for table `fs_buckets`
--
ALTER TABLE `fs_buckets`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `fs_bucket_items`
--
ALTER TABLE `fs_bucket_items`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `fs_categories`
--
ALTER TABLE `fs_categories`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `fs_orders`
--
ALTER TABLE `fs_orders`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `fs_order_items`
--
ALTER TABLE `fs_order_items`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `fs_products`
--
ALTER TABLE `fs_products`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `fs_roles`
--
ALTER TABLE `fs_roles`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `fs_users`
--
ALTER TABLE `fs_users`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;

--
-- Constraints for tables
--

--
-- Constraints for table `fs_bucket_items`
--
ALTER TABLE `fs_bucket_items`
  ADD CONSTRAINT `FK_BUCKET_ITEMS_TO_BUCKET` FOREIGN KEY (`bucket_id`) REFERENCES `fs_buckets` (`id`),
  ADD CONSTRAINT `FK_BUCKET_ITEMS_TO_PRODUCT` FOREIGN KEY (`product_id`) REFERENCES `fs_products` (`id`);

--
-- Constraints for table `fs_orders`
--
ALTER TABLE `fs_orders`
  ADD CONSTRAINT `FK_ORDERS_TO_USER` FOREIGN KEY (`user_id`) REFERENCES `fs_users` (`id`);

--
-- Constraints for table `fs_order_items`
--
ALTER TABLE `fs_order_items`
  ADD CONSTRAINT `FK_ORDER_ITEMS_TO_PRODUCT` FOREIGN KEY (`product_id`) REFERENCES `fs_products` (`id`),
  ADD CONSTRAINT `FK_ORDER_ITEMS_TO_ORDER` FOREIGN KEY (`order_id`) REFERENCES `fs_orders` (`id`);

--
-- Constraints for table `fs_products_categories`
--
ALTER TABLE `fs_products_categories`
  ADD CONSTRAINT `FK_PRODUCTS_CATEGORIES_TO_CATEGORY` FOREIGN KEY (`category_id`) REFERENCES `fs_categories` (`id`),
  ADD CONSTRAINT `FK_PRODUCTS_CATEGORIES_TO_PRODUCT` FOREIGN KEY (`product_id`) REFERENCES `fs_products` (`id`);

--
-- Constraints for table `fs_users`
--
ALTER TABLE `fs_users`
  ADD CONSTRAINT `FK_USERS_TO_BUCKET` FOREIGN KEY (`bucket_id`) REFERENCES `fs_buckets` (`id`);

--
-- Constraints for table `fs_users_roles`
--
ALTER TABLE `fs_users_roles`
  ADD CONSTRAINT `FK_USERS_ROLES_TO_USER` FOREIGN KEY (`user_id`) REFERENCES `fs_users` (`id`),
  ADD CONSTRAINT `FK_USERS_ROLES_TO_ROLE` FOREIGN KEY (`role_id`) REFERENCES `fs_roles` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
