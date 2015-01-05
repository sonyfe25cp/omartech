package com.omartech.proxyspider.model;

import java.util.List;

/**
 * Created by omar on 14-12-30.
 */
public class Image {
    private String id;
    private String desc;
    private List<String> tags;
    private Owner owner;
    private String fromPageTitle;
    private String column;
    private String parentTag;
    private String date; //yyyy-mm-dd
    private String downloadUrl;
    private String imageUrl;
    private int imageWidth;
    private int imageHeight;
    private String thumbnailUrl;
    private int thumbnailWidth;
    private int thumbnailHeight;
    private int thumbLargeWidth;
    private int thumbLargeHeight;
    private String thumbLargeUrl;
    private int thumbLargeTnWidth;
    private int thumbLargeTnHeight;
    private String thumbLargeTnUrl;
    private String siteName;
    private String siteLogo;
    private String siteUrl;
    private String fromUrl;
    private String isBook;
    private String bookId;
    private String objUrl;
    private String shareUrl;
    private String setId;
    private String albumId;
    private int isAlbum;
    private String albumName;
    private int albumNum;

    private String userId;//"62593895",
    private int isVip;//0,
    private int isDapei;//0,
    private String dressId;//"",
    private String dressBuyLink;//"",
    private int dressPrice;//0,
    private int dressDiscount;//0,
    private String dressExtInfo;//"",
    private String dressTag;//"",
    private int dressNum;//0,
    private String objTag;//"气质",
    private int dressImgNum;//0,
    private String hostName;//"www.iyi8.com",
    private String pictureId;//"14609315471",
    private String pictureSign;//"e27b23dcd63d7a2a202e856e026a113f5b0704d7",
    private String dataSrc;//"",
    private String contentSign;//"0,0",
    private String albumDi;//"",
    private String canAlbumId;//"",
    private String albumObjNum;//"",
    private String appId;//"",
    private String photoId;//"",
    private int fromName;//0,
    private String fashion;//"null",
    private String title;//"复古气质美女写真"

    @Override
    public String toString() {
        return "Image{" +
                "id='" + id + '\'' +
                ", desc='" + desc + '\'' +
                ", tags=" + tags +
                ", owner=" + owner +
                ", fromPageTitle='" + fromPageTitle + '\'' +
                ", column='" + column + '\'' +
                ", parentTag='" + parentTag + '\'' +
                ", date='" + date + '\'' +
                ", downloadUrl='" + downloadUrl + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", imageWidth=" + imageWidth +
                ", imageHeight=" + imageHeight +
                ", thumbnailUrl='" + thumbnailUrl + '\'' +
                ", thumbnailWidth=" + thumbnailWidth +
                ", thumbnailHeight=" + thumbnailHeight +
                ", thumbLargeWidth=" + thumbLargeWidth +
                ", thumbLargeHeight=" + thumbLargeHeight +
                ", thumbLargeUrl='" + thumbLargeUrl + '\'' +
                ", thumbLargeTnWidth=" + thumbLargeTnWidth +
                ", thumbLargeTnHeight=" + thumbLargeTnHeight +
                ", thumbLargeTnUrl='" + thumbLargeTnUrl + '\'' +
                ", siteName='" + siteName + '\'' +
                ", siteLogo='" + siteLogo + '\'' +
                ", siteUrl='" + siteUrl + '\'' +
                ", fromUrl='" + fromUrl + '\'' +
                ", isBook='" + isBook + '\'' +
                ", bookId='" + bookId + '\'' +
                ", objUrl='" + objUrl + '\'' +
                ", shareUrl='" + shareUrl + '\'' +
                ", setId='" + setId + '\'' +
                ", albumId='" + albumId + '\'' +
                ", isAlbum=" + isAlbum +
                ", albumName='" + albumName + '\'' +
                ", albumNum=" + albumNum +
                ", userId='" + userId + '\'' +
                ", isVip=" + isVip +
                ", isDapei=" + isDapei +
                ", dressId='" + dressId + '\'' +
                ", dressBuyLink='" + dressBuyLink + '\'' +
                ", dressPrice=" + dressPrice +
                ", dressDiscount=" + dressDiscount +
                ", dressExtInfo='" + dressExtInfo + '\'' +
                ", dressTag='" + dressTag + '\'' +
                ", dressNum=" + dressNum +
                ", objTag='" + objTag + '\'' +
                ", dressImgNum=" + dressImgNum +
                ", hostName='" + hostName + '\'' +
                ", pictureId='" + pictureId + '\'' +
                ", pictureSign='" + pictureSign + '\'' +
                ", dataSrc='" + dataSrc + '\'' +
                ", contentSign='" + contentSign + '\'' +
                ", albumDi='" + albumDi + '\'' +
                ", canAlbumId='" + canAlbumId + '\'' +
                ", albumObjNum='" + albumObjNum + '\'' +
                ", appId='" + appId + '\'' +
                ", photoId='" + photoId + '\'' +
                ", fromName=" + fromName +
                ", fashion='" + fashion + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public String getFromPageTitle() {
        return fromPageTitle;
    }

    public void setFromPageTitle(String fromPageTitle) {
        this.fromPageTitle = fromPageTitle;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getParentTag() {
        return parentTag;
    }

    public void setParentTag(String parentTag) {
        this.parentTag = parentTag;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public int getThumbnailWidth() {
        return thumbnailWidth;
    }

    public void setThumbnailWidth(int thumbnailWidth) {
        this.thumbnailWidth = thumbnailWidth;
    }

    public int getThumbnailHeight() {
        return thumbnailHeight;
    }

    public void setThumbnailHeight(int thumbnailHeight) {
        this.thumbnailHeight = thumbnailHeight;
    }

    public int getThumbLargeWidth() {
        return thumbLargeWidth;
    }

    public void setThumbLargeWidth(int thumbLargeWidth) {
        this.thumbLargeWidth = thumbLargeWidth;
    }

    public int getThumbLargeHeight() {
        return thumbLargeHeight;
    }

    public void setThumbLargeHeight(int thumbLargeHeight) {
        this.thumbLargeHeight = thumbLargeHeight;
    }

    public String getThumbLargeUrl() {
        return thumbLargeUrl;
    }

    public void setThumbLargeUrl(String thumbLargeUrl) {
        this.thumbLargeUrl = thumbLargeUrl;
    }

    public int getThumbLargeTnWidth() {
        return thumbLargeTnWidth;
    }

    public void setThumbLargeTnWidth(int thumbLargeTnWidth) {
        this.thumbLargeTnWidth = thumbLargeTnWidth;
    }

    public int getThumbLargeTnHeight() {
        return thumbLargeTnHeight;
    }

    public void setThumbLargeTnHeight(int thumbLargeTnHeight) {
        this.thumbLargeTnHeight = thumbLargeTnHeight;
    }

    public String getThumbLargeTnUrl() {
        return thumbLargeTnUrl;
    }

    public void setThumbLargeTnUrl(String thumbLargeTnUrl) {
        this.thumbLargeTnUrl = thumbLargeTnUrl;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getSiteLogo() {
        return siteLogo;
    }

    public void setSiteLogo(String siteLogo) {
        this.siteLogo = siteLogo;
    }

    public String getSiteUrl() {
        return siteUrl;
    }

    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }

    public String getFromUrl() {
        return fromUrl;
    }

    public void setFromUrl(String fromUrl) {
        this.fromUrl = fromUrl;
    }

    public String getIsBook() {
        return isBook;
    }

    public void setIsBook(String isBook) {
        this.isBook = isBook;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getObjUrl() {
        return objUrl;
    }

    public void setObjUrl(String objUrl) {
        this.objUrl = objUrl;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public String getSetId() {
        return setId;
    }

    public void setSetId(String setId) {
        this.setId = setId;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public int getIsAlbum() {
        return isAlbum;
    }

    public void setIsAlbum(int isAlbum) {
        this.isAlbum = isAlbum;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public int getAlbumNum() {
        return albumNum;
    }

    public void setAlbumNum(int albumNum) {
        this.albumNum = albumNum;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getIsVip() {
        return isVip;
    }

    public void setIsVip(int isVip) {
        this.isVip = isVip;
    }

    public int getIsDapei() {
        return isDapei;
    }

    public void setIsDapei(int isDapei) {
        this.isDapei = isDapei;
    }

    public String getDressId() {
        return dressId;
    }

    public void setDressId(String dressId) {
        this.dressId = dressId;
    }

    public String getDressBuyLink() {
        return dressBuyLink;
    }

    public void setDressBuyLink(String dressBuyLink) {
        this.dressBuyLink = dressBuyLink;
    }

    public int getDressPrice() {
        return dressPrice;
    }

    public void setDressPrice(int dressPrice) {
        this.dressPrice = dressPrice;
    }

    public int getDressDiscount() {
        return dressDiscount;
    }

    public void setDressDiscount(int dressDiscount) {
        this.dressDiscount = dressDiscount;
    }

    public String getDressExtInfo() {
        return dressExtInfo;
    }

    public void setDressExtInfo(String dressExtInfo) {
        this.dressExtInfo = dressExtInfo;
    }

    public String getDressTag() {
        return dressTag;
    }

    public void setDressTag(String dressTag) {
        this.dressTag = dressTag;
    }

    public int getDressNum() {
        return dressNum;
    }

    public void setDressNum(int dressNum) {
        this.dressNum = dressNum;
    }

    public String getObjTag() {
        return objTag;
    }

    public void setObjTag(String objTag) {
        this.objTag = objTag;
    }

    public int getDressImgNum() {
        return dressImgNum;
    }

    public void setDressImgNum(int dressImgNum) {
        this.dressImgNum = dressImgNum;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getPictureId() {
        return pictureId;
    }

    public void setPictureId(String pictureId) {
        this.pictureId = pictureId;
    }

    public String getPictureSign() {
        return pictureSign;
    }

    public void setPictureSign(String pictureSign) {
        this.pictureSign = pictureSign;
    }

    public String getDataSrc() {
        return dataSrc;
    }

    public void setDataSrc(String dataSrc) {
        this.dataSrc = dataSrc;
    }

    public String getContentSign() {
        return contentSign;
    }

    public void setContentSign(String contentSign) {
        this.contentSign = contentSign;
    }

    public String getAlbumDi() {
        return albumDi;
    }

    public void setAlbumDi(String albumDi) {
        this.albumDi = albumDi;
    }

    public String getCanAlbumId() {
        return canAlbumId;
    }

    public void setCanAlbumId(String canAlbumId) {
        this.canAlbumId = canAlbumId;
    }

    public String getAlbumObjNum() {
        return albumObjNum;
    }

    public void setAlbumObjNum(String albumObjNum) {
        this.albumObjNum = albumObjNum;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    public int getFromName() {
        return fromName;
    }

    public void setFromName(int fromName) {
        this.fromName = fromName;
    }

    public String getFashion() {
        return fashion;
    }

    public void setFashion(String fashion) {
        this.fashion = fashion;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}