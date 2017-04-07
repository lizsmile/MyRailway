package com.example.liz.myrailway;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2017/4/7.
 */

public class ExpandableListViewaAdapter extends BaseExpandableListAdapter {
    private List<String> listGroup = new ArrayList<String>();
    private List<List<String>> listChild = new ArrayList<List<String>>();
    Activity activity;
    private Context context;
    public  ExpandableListViewaAdapter(Context context, List<String> listGroup,
                                       List<List<String>> listChild)
    {
        super();
        this.context = context;
        this.listGroup = listGroup;
        this.listChild = listChild;
    }
    @Override
    public int getGroupCount() {
        // TODO Auto-generated method stub
        return listGroup.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        // TODO Auto-generated method stub
        return listChild.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        // TODO Auto-generated method stub
        return listGroup.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return childPosition;
    }

    @Override
    public long getGroupId(int groupPosition) {
        // TODO Auto-generated method stub
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        convertView = LinearLayout.inflate(context, R.layout.group_item, null);
        TextView text_group = (TextView) convertView.findViewById(R.id.group_text);
        text_group.setText(listGroup.get(groupPosition));

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        convertView = LinearLayout.inflate(context, R.layout.child_item, null);
        TextView text_child = (TextView) convertView.findViewById(R.id.text);
        text_child.setText(listChild.get(groupPosition).get(childPosition));

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return true;
    }
}
