
###Rules

####Prio

FirstMatch > Contains > Link


###Logic

Query -> Parser -> Filter

if match
    -> reply
else
    Find key word in query -> search
end


Reply -> wrapper
    -> NormalWrapper
    -> PictureWrapper
    -> CompareWrapper

###DB table

1. Article

2. Filter
    id, filterType, keyWord, replyId, replyType
3. QueryLog
    uid, time, keyWord, filterId, articleId, source


###Art

Client:
    1. QueryParser
    2. ReplyWrapper
    3. LogSender



SearchServer:
    1. BuildIndex
    2. SearchIndex
    3. Log



##Games
1. brain, ykganmes:
    http://hudong.pl.youku.com/v/braingame?jump=success





