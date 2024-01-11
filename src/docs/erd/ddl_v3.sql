CREATE TABLE `users` (
  `id` bigint PRIMARY KEY,
  `nickname` varchar(255),
  `status` varchar(255),
  `role` varchar(255),
  `grade` varchar(255),
  `name` varchar(255),
  `email` varchar(255),
  `default_delivery_address_id` bigint,
  `mobile_number` varchar(255),
  `birthday` datetime,
  `gender` varchar(255),
  `password` varchar(255),
  `created_at` datetime,
  `updated_at` datetime
);

CREATE TABLE `addresses` (
  `id` bigint PRIMARY KEY,
  `user_id` bigint,
  `address` varchar(255),
  `address_detail` varchar(255),
  `zip_code` varchar(255),
  `created_at` datetime,
  `updated_at` datetime
);

CREATE TABLE `carts` (
  `id` bigint PRIMARY KEY,
  `user_id` bigint,
  `created_at` datetime,
  `updated_at` datetime
);

CREATE TABLE `cart_product` (
  `id` bigint PRIMARY KEY,
  `cart_id` bigint,
  `product_id` bigint,
  `quantity` int,
  `product_price` bigdecimal,
  `created_at` datetime,
  `updated_at` datetime
);

CREATE TABLE `reserves` (
  `id` bigint PRIMARY KEY,
  `user_id` bigint,
  `order_id` bigint,
  `is_valid` boolean,
  `amount` bigdecimal,
  `created_at` datetime,
  `updated_at` datetime
);

CREATE TABLE `orders` (
  `id` bigint PRIMARY KEY,
  `user_id` bigint,
  `delivery_id` bigint,
  `status` varchar(255),
  `created_at` datetime,
  `updated_at` datetime
);

CREATE TABLE `order_product` (
  `id` bigint PRIMARY KEY,
  `order_id` bigint,
  `product_id` bigint,
  `quantity` int,
  `product_price` bigdecimal,
  `created_at` datetime,
  `updated_at` datetime
);

CREATE TABLE `storages` (
  `id` bigint PRIMARY KEY,
  `type` varchar(255),
  `created_at` datetime,
  `updated_at` datetime
);

CREATE TABLE `products` (
  `id` bigint PRIMARY KEY,
  `storage_id` bigint,
  `seller_id` bigint,
  `product_no` bigint,
  `short_description` varchar(255),
  `allergy` varchar(255),
  `delivery_type_name` json,
  `expiration_date` datetime,
  `main_image_url` varchar(255),
  `created_at` datetime,
  `updated_at` datetime
);

CREATE TABLE `sub_product` (
  `id` bigint PRIMARY KEY,
  `product_id` bigint,
  `category_id` bigint,
  `name` varchar(255),
  `brand` varchar(255),
  `tag` json,
  `base_price` bigdecimal,
  `retail_price` bigdecimal,
  `discount_rate` int,
  `dicounted_price` bigdecimal,
  `restock` int,
  `can_restock_notify` boolean,
  `min_quantity` int,
  `max_quantity` int,
  `is_sold_out` boolean,
  `is_purchase_status` boolean,
  `is_expected_point` boolean,
  `created_at` datetime,
  `updated_at` datetime
);

CREATE TABLE `categories` (
  `id` bigint PRIMARY KEY,
  `name` varchar(255),
  `created_at` datetime,
  `updated_at` datetime
);

CREATE TABLE `deliveries` (
  `id` bigint PRIMARY KEY,
  `address_id` bigint,
  `status` varchar(255),
  `created_at` datetime,
  `updated_at` datetime
);

CREATE TABLE `sellers` (
  `id` bigint PRIMARY KEY,
  `name` string,
  `created_at` datetime,
  `updated_at` datetime
);

ALTER TABLE `addresses` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `reserves` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `orders` ADD FOREIGN KEY (`id`) REFERENCES `reserves` (`order_id`);

ALTER TABLE `users` ADD FOREIGN KEY (`id`) REFERENCES `carts` (`user_id`);

ALTER TABLE `cart_product` ADD FOREIGN KEY (`cart_id`) REFERENCES `carts` (`id`);

ALTER TABLE `cart_product` ADD FOREIGN KEY (`product_id`) REFERENCES `products` (`id`);

ALTER TABLE `orders` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `deliveries` ADD FOREIGN KEY (`id`) REFERENCES `orders` (`delivery_id`);

ALTER TABLE `order_product` ADD FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`);

ALTER TABLE `order_product` ADD FOREIGN KEY (`product_id`) REFERENCES `products` (`id`);

ALTER TABLE `sub_product` ADD FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`);

ALTER TABLE `sub_product` ADD FOREIGN KEY (`product_id`) REFERENCES `products` (`id`);

ALTER TABLE `storages` ADD FOREIGN KEY (`id`) REFERENCES `products` (`storage_id`);

ALTER TABLE `products` ADD FOREIGN KEY (`seller_id`) REFERENCES `sellers` (`id`);

ALTER TABLE `addresses` ADD FOREIGN KEY (`id`) REFERENCES `deliveries` (`address_id`);
