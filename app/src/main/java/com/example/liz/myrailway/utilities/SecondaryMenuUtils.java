package com.example.liz.myrailway.utilities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.liz.myrailway.ExpandableListViewaAdapter;
import com.example.liz.myrailway.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2017/4/7.
 */

public class SecondaryMenuUtils {
    private Context context;
    private ExpandableListView expandableListView; // Layout expandablelist.xml
    private List<String> listGroup = new ArrayList<String>();
    private List<List<String>> listChild = new ArrayList<List<String>>();

    private String[] groupDate;//一级目录数据

    private String[][] childDate;//对应的二级目录数据



    public SecondaryMenuUtils(Context context,
                              ExpandableListView expandableListView, List<String> listGroup,
                              List<List<String>> listChild, String[] groupDate,
                              String[][] childDate) {
        super();
        this.context = context;
        this.expandableListView = expandableListView;
        this.listGroup = listGroup;
        this.listChild = listChild;
        this.groupDate = groupDate;
        this.childDate = childDate;
    }

    /**
     * 添加二级菜单数据
     *
     * @param groupDate
     */
    public void addDate(String[] groupDate) {
        // TODO Auto-generated method stub
        for (int i = 0; i < groupDate.length; i++) {
            String group_text = groupDate[i];// 获取每一个一级菜单text
            listGroup.add(group_text);
            List<String> children = new ArrayList<String>();
            if (listGroup.get(i).equals(group_text)) {
                mforTwoArrays(children,childDate,i);
            } else if (listGroup.get(i).equals(group_text)) {
                mforTwoArrays(children,childDate,i);
            } else if (listGroup.get(i).equals(group_text)) {
                mforTwoArrays(children,childDate,i);
            } else {
                mforTwoArrays(children,childDate,i);
            }

            listChild.add(children);
        }

    }

    private void mforTwoArrays(List<String> children,String[][] arrs, int j){
        String[] arr2 = arrs[j];
        for (int c = 0; c < arr2.length; c++) {
            children.add(arr2[c]);
        }
    }

    public void createExpandableListViewDialog(final EditText editText_child) {
        // 设给弹出窗口的view
        final Dialog dialog; // 弹出类似Spinner选择项的窗口
        View viewList = ((Activity) context).getLayoutInflater().inflate(
                R.layout.city_listview, null);
        dialog = new Dialog(context);
        dialog.show();
        dialog.setContentView(viewList);
        expandableListView = (ExpandableListView) viewList.findViewById(R.id.city_list);
        // 绑定ExpandableListView的数据
        ExpandableListViewaAdapter mAdapter = new ExpandableListViewaAdapter(context,listGroup,listChild);
        expandableListView.setAdapter(mAdapter);

        /**
         * 1 单击事件 返回 false表示不触发单击事件
         */
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return false;
            }

        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View view,
                                        int groupPosition, int childPosition, long id)
                    throws RuntimeException {
                Toast.makeText(context, listGroup.get(groupPosition)
                        + "--->>>"
                        + listChild.get(groupPosition)
                        .get(childPosition).toString(), Toast.LENGTH_SHORT)
                        .show();
                // editText1.setText(listGroup.get(groupPosition)
                // + "下的"
                // + listChild.get(groupPosition).get(childPosition)
                // .toString());
                editText_child.setText(listChild.get(groupPosition)
                        .get(childPosition).toString());

                dialog.dismiss();
                return true;
            }
        });
    }

}
