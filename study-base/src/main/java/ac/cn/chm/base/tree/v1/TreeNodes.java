package ac.cn.chm.base.tree.v1;

import com.alibaba.druid.util.StringUtils;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TreeNodes implements Comparable<TreeNodes> {//实现Comparable接口，实现根据level排序
    //实体类主键(必须字段)，用于与上级绑定
    private String id;
    //父级ID(必须字段)，用于与下级绑定
    private String pId;
    //实体类层级(必须字段)
    private String level;
    //实体类名称(非必须，可替换为其它一个或多个参数)
    private String text;
    private List<TreeNodes> children = new ArrayList<>();

    public TreeNodes() {
    }

    ;

    /**
     * @param id    一般为主键
     * @param pId   父id
     * @param text  标签名称
     * @param level 层级
     */
    public TreeNodes(String id, String pId, String text, String level) {
        this.id = StringUtils.isEmpty(id) ? "id" : id;
        this.pId = StringUtils.isEmpty(pId) ? "pId" : pId;
        this.text = StringUtils.isEmpty(text) ? "text" : text;//非必须，可替换为其它一个或多个参数
        this.level = StringUtils.isEmpty(level) ? "level" : level;
    }

    @Override
    public int compareTo(TreeNodes tn) {
        //1排在当前的后面//-1排在当前的前面
        int big = 1, small = -1, eq = 0;
        if (tn == null || tn.getLevel() == null) {
            return big;
        } else if (this.getLevel() == null) {
            return small;
        } else if (Integer.valueOf(tn.getLevel()) < Integer.valueOf(this.getLevel())) {
            return big;
        } else if (Integer.valueOf(tn.getLevel()) > Integer.valueOf(this.getLevel())) {
            return small;
        }
        return eq;
    }
    //省略getter、setter方法
}