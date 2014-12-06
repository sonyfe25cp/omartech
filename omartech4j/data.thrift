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
  5: ArticleType articleType = ArticleType.Bican,
}

struct ArticleRequest{
  1: string keyword,
  2: i32 offset,
  3: i32 limit,
}

struct ArticleResponse{
  1: string keyword,
  2: list<Article> articles,
  3: i32 total,
}

service DataService{

  ArticleResponse searchArticle(1: ArticleRequest req) //查询文章

  void insertArticle(1: Article article) //保存文章

  
}
