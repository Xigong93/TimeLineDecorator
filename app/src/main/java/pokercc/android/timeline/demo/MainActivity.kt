package pokercc.android.timeline.demo

import android.graphics.Rect
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import kotlinx.android.synthetic.main.activity_main.*
import pokercc.android.timeline.TimeLineDecorator
import pokercc.android.timeline.ThroughTimeLineDecorator


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "美柚大事件"
        val data = kotlin.collections.listOf(
            Item("开发首款产品", "伟大若有初衷，都出自渺小的理由十余人团队，同一个方向由工具开始，从经期出发只想让女生的世界变得不一样"),
            Item("美柚App正式上线", "初始化的进程很卡，起跑需要加速度（118°E，24°N）Xiamen，30天封闭式打磨美柚1.0版iOS、Android双平台发布梦想在不断迭代中萌芽，愈发明亮"),
            Item("新增她她圈社区", "分享与倾诉，是女人的天性宠爱的下一步，是圈出一个她的国度短短5个月，千万妹纸因爱驻足含苞待放的生命力在此觉醒"),
            Item("C轮融资", "同频吸引，往往源自爱上同一件事1年3次融资，圆梦的力量开始疯长更多的人因为同样的信念聚集一起参与让全世界女性变得更好的构想"),
            Item("柚宝宝App上线", "脐带连接生命，情怀延长使命应孕而生的柚宝宝，像纽带牵系着母婴在女人完整的生命周期里给她多一份陪伴，多一倍保护"),
            //
            Item("美柚用户数突破1亿", "信仰的磁场指引我们惯性滑跑一如既往的用心，成就女儿国的基因集上亿宠爱，于互联浪潮中屹立上风梦想像雪球一样，越翻滚越强大"),
            Item("柚宝宝相册App上线", "宝贝成长是母亲最想珍藏的财富用记录，赋予童年声动的可能用照片，将美好点滴串成回忆一路相伴，分享育儿感动"),
            Item("柚子街App上线/D轮融资", "买买买是对女生最好的治愈物欲的追求需要一个省钱的理由3个月突破日订单40万步履不停，持续为梦想正名"),
            Item("柚子街月销售额突破1亿元", "从零到亿，是社区通往电商的距离从试水到领跑，是点滴沉淀的质变让你更美的追求不改里程上的数字仍在继续"),
            Item("柚宝宝成为优质亲子育儿社区", "十月怀胎，呱呱坠地，是最美的愿望每一个幸福时刻，我们都在左右成长的历程中，每一步都是印记追逐梦想的脚步，从不停歇"),
            //
            Item("美柚净利润超过1000万元", "沉默，不是静待不动而是滴水穿石般润物无声梦想和现实的齿轮相互契合三年力量的积蓄，只待一鸣惊人"),
            Item("获E轮10亿元融资，完成VIE架构拆除，回归国内资本", "回归，因为信仰、因为专注初心一如初见历经风霜雨雪仍坚定向前终如红梅般绽放于资本寒冬"),
            Item("双11当日订单量突破132万单，同比增长230%", "满当当的生活少不了购购购的需求成绩的背后是许多个日夜的坚守让突破成为一种习惯小目标终会成大梦想"),
            Item("美柚依然在生长", "不忘初心，从心出发用未来的眼光，做最初的事未来，我们依然懂你将服务女性的事业进行到底")

        )
        recyclerView.adapter = TimeAdapter(data)
        val headerDrawable = resources.getDrawable(R.drawable.header).mutate() as GradientDrawable
        headerDrawable.setSize(22, 22)


        val lineDrawable = resources.getDrawable(R.drawable.line).mutate() as GradientDrawable

        lineDrawable.setSize(4, 8)
        recyclerView.addItemDecoration(
            TimeLineDecorator(
                headerDrawable, lineDrawable,
                Rect(40, 48, 40, 30), 8

            )
        )
    }

    fun onChangeTimeLineClick(view: View) {
        val headerDrawable = resources.getDrawable(R.drawable.header).mutate() as GradientDrawable
        headerDrawable.setSize(22, 22)


        val lineDrawable = resources.getDrawable(R.drawable.line).mutate() as GradientDrawable

        AlertDialog.Builder(this)
            .setItems(arrayOf("普通", "连线式(固定头Margin)", "连线式(计算头Margin)", "连线式(跳过第二个Item进行绘制)")) { dialog, which ->
                when (which) {
                    0 -> {
                        recyclerView.removeItemDecorationAt(0)
                        recyclerView.addItemDecoration(
                            TimeLineDecorator(
                                headerDrawable, lineDrawable,
                                Rect(40, 48, 40, 30), 8

                            )
                        )
                    }
                    1 -> {
                        // 直接标一个固定值，设置头的间距
                        recyclerView.removeItemDecorationAt(0)
                        recyclerView.addItemDecoration(
                            ThroughTimeLineDecorator(
                                headerDrawable, lineDrawable,
                                5, 40, 48, false
                            )
                        )
                    }
                    2 -> {
                        // 动态计算头的间距
                        recyclerView.removeItemDecorationAt(0)
                        recyclerView.addItemDecoration(
                            ThroughTimeLineDecorator(
                                headerDrawable, lineDrawable,
                                40, 40, false
                            ) { childView: View ->
                                val tvTitle = childView.findViewById<View>(R.id.tv_title)
                                val parentLocation = IntArray(2)
                                childView.getLocationInWindow(parentLocation)
                                val childLocation = IntArray(2)
                                tvTitle.getLocationInWindow(childLocation)
                                // 这只是TextView一行的情况下，多行的需要计算TextView出行高
                                val marginTop = childLocation[1] - parentLocation[1] + tvTitle.height / 2
                                marginTop
                            }
                        )
                    }
                    3 -> {
                        // 直接标一个固定值，设置头的间距
                        recyclerView.removeItemDecorationAt(0)
                        recyclerView.addItemDecoration(
                            ThroughTimeLineDecorator(
                                headerDrawable, lineDrawable,
                                5, 40, 48, false
                            ).apply {
                                setTypeHandler { position, itemType ->
                                    position != 1
                                }
                            }
                        )
                    }
                }
            }.show()
    }
}

data class Item(
    val title: String,
    val desc: String
)

class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val tvTitle: TextView by lazy { itemView.findViewById<TextView>(R.id.tv_title) }
    val tvDesc: TextView by lazy { itemView.findViewById<TextView>(R.id.tv_desc) }
}

class TimeAdapter(private val items: List<Item>) : Adapter<ItemViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ItemViewHolder {
        return ItemViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.item, p0, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(p0: ItemViewHolder, p1: Int) {

        p0.tvTitle.text = items[p1].title
        p0.tvDesc.text = items[p1].desc
    }

}