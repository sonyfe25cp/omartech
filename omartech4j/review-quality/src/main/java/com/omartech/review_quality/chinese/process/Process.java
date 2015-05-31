package com.omartech.review_quality.chinese.process;

import org.ansj.app.keyword.KeyWordComputer;
import org.ansj.app.keyword.Keyword;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.NlpAnalysis;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.List;

/**
 * Created by OmarTech on 15-3-21.
 */
public class Process {
    public static void main(String[] args) {
        KeyWordComputer kwc = new KeyWordComputer(5);
        String title = "";
//        String title = "维基解密否认斯诺登接受委内瑞拉庇护";
//        String content = "有俄罗斯国会议员，9号在社交网站推特表示，美国中情局前雇员斯诺登，已经接受委内瑞拉的庇护，不过推文在发布几分钟后随即删除。俄罗斯当局拒绝发表评论，而一直协助斯诺登的维基解密否认他将投靠委内瑞拉。　　俄罗斯国会国际事务委员会主席普什科夫，在个人推特率先披露斯诺登已接受委内瑞拉的庇护建议，令外界以为斯诺登的动向终于有新进展。　　不过推文在几分钟内旋即被删除，普什科夫澄清他是看到俄罗斯国营电视台的新闻才这样说，而电视台已经作出否认，称普什科夫是误解了新闻内容。　　委内瑞拉驻莫斯科大使馆、俄罗斯总统府发言人、以及外交部都拒绝发表评论。而维基解密就否认斯诺登已正式接受委内瑞拉的庇护，说会在适当时间公布有关决定。　　斯诺登相信目前还在莫斯科谢列梅捷沃机场，已滞留两个多星期。他早前向约20个国家提交庇护申请，委内瑞拉、尼加拉瓜和玻利维亚，先后表示答应，不过斯诺登还没作出决定。　　而另一场外交风波，玻利维亚总统莫拉莱斯的专机上星期被欧洲多国以怀疑斯诺登在机上为由拒绝过境事件，涉事国家之一的西班牙突然转口风，外长马加略]号表示愿意就任何误解致歉，但强调当时当局没有关闭领空或不许专机降落。";
        String content = "首先说发展,奇瑞这两年发展形式比较好,不过盈利能力还不算太强,因为这两年竞争太激烈,降价很厉害.在国内的汽车行业,如果你真想学东西,到奇瑞也还算是一个不错的选择.\n" +
                " 再说工作负荷.在奇瑞干活比较累,因为项目太多了,另外公司规定一周上6天班,研究生没有加班费,周日加班也不给加班费,本科生是有加班费的.不过对于年轻人来说,学习才是重要的,关键是一个学习的环境,我去年研究生毕业来的这里.\n" +
                " 接下来谈一下待遇,奇瑞这两年大量招人,我来的时候8000多人,现在13000左右了,所以工资开销成了它很大的一个成本开销,另外这两年分出了很多机构,多了好多领导,这些领导一个人拿的钱顶下面n多人拿的钱,所以待遇方面是一年不如一年了(对于新来的人说).我去年来的时候,试用期4个月,拿到手2000多一点,转正后拿到手3000,没有外面说的那么高3500是不大可能的.另外住房方面现在也不如以前了,现在研究生是两个人一套房子:两室一厅的.不过芜湖的消费水平不算高,但是房价也不低.\n" +
                " 最后说一下周围生活环境,我们住在开发区,到市区要1个小时,平时没时间,周末累的在家里睡觉,另外就是打扫卫生,所以基本生没什么时间出去娱乐的.跟学校里差不多,不过习惯就好了。";
        Collection<Keyword> result = kwc.computeArticleTfidf(title, content);
        System.out.println(result);

        List<Term> parse = ToAnalysis.parse(content);
        for(Term t : parse){
            String name = t.getName();
            if(!StringUtils.isEmpty(name) && name.length() > 1){
                System.out.print(name+" ");
            }
        }
        System.out.println(parse);

    }
}
