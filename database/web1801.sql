-- phpMyAdmin SQL Dump
-- version 4.7.7
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th3 04, 2018 lúc 04:20 AM
-- Phiên bản máy phục vụ: 10.1.30-MariaDB
-- Phiên bản PHP: 7.2.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `web1801`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `quyen`
--

CREATE TABLE `quyen` (
  `quyen_id` int(11) NOT NULL,
  `quyen_ten` varchar(100) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `quyen`
--

INSERT INTO `quyen` (`quyen_id`, `quyen_ten`) VALUES
(0, 'Guest'),
(1, 'Admin');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `quyen_taikhoan`
--

CREATE TABLE `quyen_taikhoan` (
  `qtk_id` int(11) NOT NULL,
  `quyen_id` int(11) NOT NULL,
  `tk_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `quyen_taikhoan`
--

INSERT INTO `quyen_taikhoan` (`qtk_id`, `quyen_id`, `tk_id`) VALUES
(1, 1, 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `taikhoan`
--

CREATE TABLE `taikhoan` (
  `tk_id` int(11) NOT NULL,
  `tk_tenhienthi` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `tk_tentaikhoan` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `tk_matkhau` varchar(64) COLLATE utf8_unicode_ci NOT NULL,
  `tk_ngaytao` date NOT NULL,
  `tk_nguoitao` varchar(32) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `taikhoan`
--

INSERT INTO `taikhoan` (`tk_id`, `tk_tenhienthi`, `tk_tentaikhoan`, `tk_matkhau`, `tk_ngaytao`, `tk_nguoitao`) VALUES
(1, 'Nguyễn Phi Vũ', 'npvu', 'sunny101100', '2018-03-04', '');

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `quyen`
--
ALTER TABLE `quyen`
  ADD PRIMARY KEY (`quyen_id`);

--
-- Chỉ mục cho bảng `quyen_taikhoan`
--
ALTER TABLE `quyen_taikhoan`
  ADD PRIMARY KEY (`qtk_id`),
  ADD KEY `fk_quyen` (`quyen_id`),
  ADD KEY `fk_tk` (`tk_id`);

--
-- Chỉ mục cho bảng `taikhoan`
--
ALTER TABLE `taikhoan`
  ADD PRIMARY KEY (`tk_id`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `quyen`
--
ALTER TABLE `quyen`
  MODIFY `quyen_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT cho bảng `quyen_taikhoan`
--
ALTER TABLE `quyen_taikhoan`
  MODIFY `qtk_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT cho bảng `taikhoan`
--
ALTER TABLE `taikhoan`
  MODIFY `tk_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `quyen_taikhoan`
--
ALTER TABLE `quyen_taikhoan`
  ADD CONSTRAINT `fk_quyen` FOREIGN KEY (`quyen_id`) REFERENCES `quyen` (`quyen_id`),
  ADD CONSTRAINT `fk_tk` FOREIGN KEY (`tk_id`) REFERENCES `taikhoan` (`tk_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
