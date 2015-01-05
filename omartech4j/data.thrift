namespace java cn.techwolf.data.gen

// thrift -gen python data.thrift
// thrift -gen java data.thrift

enum ArticleType{
  Xiaohua = 1,
  Bican = 2,
  Shudong = 3,
  Other = 4 
}

struct Article{
  1: i64 id,
  2: string title,
  3: string content,
  4: string createdAt,
  5: ArticleType articleType,
}

struct ArticleRequest{
  1: string keyword,
  2: i32 offset,
  3: i32 limit,
  4: list<i64> ids
}

struct ArticleResponse{
  1: string keyword,
  2: list<Article> articles,
  3: i32 total,
}

struct Shareholder{
  1: i64 id,
  2: string name,
  3: string type,//类型
  4: i32 theoryNum,//认缴出资额
  5: i32 factNum, //实际出资额
  6: string regMoneyUnit,//币种

}

enum PositionType{
  Dongshizhang = 1,//董事长
  Dongshi = 2,//董事
  Jingli = 3,//经理
  Jianshi = 4,//监事
}

struct Person{
  1: i64 id,
  2: string name,
  3: PositionType positionType,
}

struct Beauty{//美女图
  1: i32 id,
  2: string tags,
  3: string downloadUrl,
  4: string thumbnailUrl,
  5: string thumbLargeUrl,
  6: string thumbLargeTnUrl
}
struct BeautyRequest{
  1: string query,
  2: i32 offset,
  3: i32 limit
}
struct BeautyResponse{
  1: string query,
  2: list<Beauty> beauties,
  3: i32 total,
}

struct Qiyexinxi{//企业信息
  1: string name,
  2: string reg_id,
  3: string status,
  4: string type,
  5: string foundTime,
  6: string lawMan,
  7: i32 regMoney,//注册资本
  8: string regMoneyUnit,//币种
  9: string regAddress,
  10: string openTime,
  11: string closeTime,
  12: string regOffice,
  13: string okTime,//发照时间
  14: string bussinessScope,
  15: list<Shareholder> shareholders,//股东信息
  16: list<Person> members,//成员

}

struct QiyexinxiRequest{
  1: string name,
  2: i32 offset = 0,
  3: i32 limit = 10,
}

struct QieyexinxiResponse{
  1: string name,
  2: i32 total,
  3: list<Qiyexinxi> qiyexinxis,
}

service DataService{

  ArticleResponse searchArticle(1: ArticleRequest req) //查询文章

  ArticleResponse insertArticle(1: Article article) //保存文章

  BeautyResponse searchBeauty(1: BeautyRequest req)

  QieyexinxiResponse searchQiyexinxi(1: QiyexinxiRequest req)// 查询企业信息
  
}
