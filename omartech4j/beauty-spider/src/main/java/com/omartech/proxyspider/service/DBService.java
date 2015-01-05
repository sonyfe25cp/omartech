package com.omartech.proxyspider.service;

import com.omartech.proxyspider.model.Image;
import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * User: ChenJie
 * Date: 25/10/14
 * Time: 11:30 AM
 */
public class DBService {

    public static boolean inserOrNot(Image image, Connection conn) throws Exception {
        if (image == null || StringUtils.isEmpty(image.getDownloadUrl())) {
            return false;
        }
        String downloadUrl = image.getDownloadUrl();
        Image find = findImageByUrl(downloadUrl, conn);
        if (find == null) {
            insert(image, conn);
            return true;
        } else {
            return false;
        }
    }

    public static void insert(Image object, Connection conn) throws Exception {
        String sql = "INSERT INTO images(`id`, `col`, `description`, `tags`, `date`, `downloadUrl`, `imageUrl`, `imageWidth`, `imageHeight`, `thumbnailUrl`, `thumbnailWidth`, `thumbnailHeight`, `thumbLargeUrl`, `thumbLargeWidth`, `thumbLargeHeight`, `thumbLargeTnUrl`, `thumbLargeTnWidth`, `thumbLargeTnHeight`, `siteName`, `siteLogo`, `siteUrl`, `fromUrl`, `objUrl`, `shareUrl`, `userId`, `isVip`, `objTag`, `hostName`, `pictureId`, `title`) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try (PreparedStatement psmt = conn.prepareStatement(sql);) {
            psmt.setLong(1, Long.parseLong(object.getId()));//id
            psmt.setString(2, object.getColumn());//col
            psmt.setString(3, object.getDesc());//description
            psmt.setString(4, object.getTags().toString());//tags
            psmt.setString(5, object.getDate());//date
            psmt.setString(6, object.getDownloadUrl());//downloadUrl
            psmt.setString(7, object.getImageUrl());//imageUrl
            psmt.setInt(8, object.getImageWidth());//imageWidth
            psmt.setInt(9, object.getImageHeight());//imageHeight
            psmt.setString(10, object.getThumbnailUrl());//thumbnailUrl
            psmt.setInt(11, object.getThumbnailWidth());//thumbnailWidth
            psmt.setInt(12, object.getThumbnailHeight());//thumbnailHeight
            psmt.setString(13, object.getThumbLargeUrl());//thumbLargeUrl
            psmt.setInt(14, object.getThumbLargeWidth());//thumbLargeWidth
            psmt.setInt(15, object.getThumbLargeHeight());//thumbLargeHeight
            psmt.setString(16, object.getThumbLargeTnUrl());//thumbLargeTnUrl
            psmt.setInt(17, object.getThumbLargeTnWidth());//thumbLargeTnWidth
            psmt.setInt(18, object.getThumbLargeTnHeight());//thumbLargeTnHeight
            psmt.setString(19, object.getSiteName());//siteName
            psmt.setString(20, object.getSiteLogo());//siteLogo
            psmt.setString(21, object.getSiteUrl());//siteUrl
            psmt.setString(22, object.getFromUrl());//fromUrl
            psmt.setString(23, object.getObjUrl());//objUrl
            psmt.setString(24, object.getShareUrl());//shareUrl
            psmt.setString(25, object.getUserId());//userId
            psmt.setInt(26, object.getIsVip());//isVip
            psmt.setString(27, object.getObjTag());//objTag
            psmt.setString(28, object.getHostName());//hostName
            psmt.setString(29, object.getPictureId());//pictureId
            psmt.setString(30, object.getTitle());//title
            psmt.execute();
        }
    }

    public static Image findImageByUrl(String url, Connection conn) throws SQLException {
        Image object = null;
        String sql = "SELECT id, col, description, tags, date, downloadUrl, imageUrl, imageWidth, imageHeight, thumbnailUrl, thumbnailWidth, thumbnailHeight, thumbLargeUrl, thumbLargeWidth, thumbLargeHeight, thumbLargeTnUrl, thumbLargeTnWidth, thumbLargeTnHeight, siteName, siteLogo, siteUrl, fromUrl, objUrl, shareUrl, userId, isVip, objTag, hostName, pictureId, title FROM images WHERE downloadUrl = ?";
        try (
                PreparedStatement psmt = conn.prepareStatement(sql);
        ) {
            psmt.setString(1, url);
            try (
                    ResultSet rs = psmt.executeQuery();) {
                if (rs.next()) {
                    object = new Image();
                    long id = rs.getLong("id");
                    object.setId(id + "");
                    String col = rs.getString("col");
                    object.setColumn(col);
                    String description = rs.getString("description");
                    object.setDesc(description);
                    String tags = rs.getString("tags");

                    List<String> strings = Arrays.asList(tags.split(","));

                    object.setTags(strings);
                    String date = rs.getString("date");
//                    java.util.Date date1 = new java.util.Date(date.getTime());
//                    object.setDate(DateFormatUtils.format(date1, "yyyy-MM-dd"));
                    object.setDate(date);
                    String downloadUrl = rs.getString("downloadUrl");
                    object.setDownloadUrl(downloadUrl);
                    String imageUrl = rs.getString("imageUrl");
                    object.setImageUrl(imageUrl);
                    int imageWidth = rs.getInt("imageWidth");
                    object.setImageWidth(imageWidth);
                    int imageHeight = rs.getInt("imageHeight");
                    object.setImageHeight(imageHeight);
                    String thumbnailUrl = rs.getString("thumbnailUrl");
                    object.setThumbnailUrl(thumbnailUrl);
                    int thumbnailWidth = rs.getInt("thumbnailWidth");
                    object.setThumbnailWidth(thumbnailWidth);
                    int thumbnailHeight = rs.getInt("thumbnailHeight");
                    object.setThumbnailHeight(thumbnailHeight);
                    String thumbLargeUrl = rs.getString("thumbLargeUrl");
                    object.setThumbLargeUrl(thumbLargeUrl);
                    int thumbLargeWidth = rs.getInt("thumbLargeWidth");
                    object.setThumbLargeWidth(thumbLargeWidth);
                    int thumbLargeHeight = rs.getInt("thumbLargeHeight");
                    object.setThumbLargeHeight(thumbLargeHeight);
                    String thumbLargeTnUrl = rs.getString("thumbLargeTnUrl");
                    object.setThumbLargeTnUrl(thumbLargeTnUrl);
                    int thumbLargeTnWidth = rs.getInt("thumbLargeTnWidth");
                    object.setThumbLargeTnWidth(thumbLargeTnWidth);
                    int thumbLargeTnHeight = rs.getInt("thumbLargeTnHeight");
                    object.setThumbLargeTnHeight(thumbLargeTnHeight);
                    String siteName = rs.getString("siteName");
                    object.setSiteName(siteName);
                    String siteLogo = rs.getString("siteLogo");
                    object.setSiteLogo(siteLogo);
                    String siteUrl = rs.getString("siteUrl");
                    object.setSiteUrl(siteUrl);
                    String fromUrl = rs.getString("fromUrl");
                    object.setFromUrl(fromUrl);
                    String objUrl = rs.getString("objUrl");
                    object.setObjUrl(objUrl);
                    String shareUrl = rs.getString("shareUrl");
                    object.setShareUrl(shareUrl);
                    String userId = rs.getString("userId");
                    object.setUserId(userId);
                    int isVip = rs.getInt("isVip");
                    object.setIsVip(isVip);
                    String objTag = rs.getString("objTag");
                    object.setObjTag(objTag);
                    String hostName = rs.getString("hostName");
                    object.setHostName(hostName);
                    String pictureId = rs.getString("pictureId");
                    object.setPictureId(pictureId);
                    String title = rs.getString("title");
                    object.setTitle(title);
                }
            }
            return object;
        }


    }
}
