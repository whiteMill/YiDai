package com.sdot.yidai.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sdot.yidai.R;
import com.sdot.yidai.model.Employee;

import java.util.List;

/**
 * Created by windMill on 2017/12/15.
 */

public class StaffAdapter extends BaseAdapter {

    Context context;
    List<Employee> employeeList;

    public StaffAdapter(Context context, List<Employee> employeeList) {
        this.context = context;
        this.employeeList = employeeList;
    }

    @Override
    public int getCount() {
        return employeeList.size();
    }

    @Override
    public Object getItem(int i) {
        return employeeList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view==null){
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.employee_adapter_layout,null);
            viewHolder.employeeName =view.findViewById(R.id.employee_name);
            viewHolder.employeePhone = view.findViewById(R.id.employee_phone);
            view.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) view.getTag();
        viewHolder.employeePhone.setText(employeeList.get(i).getUsername());
        viewHolder.employeeName.setText(employeeList.get(i).getRealname());
        return view;
    }

    class ViewHolder{
        TextView employeeName;
        TextView employeePhone;
    }
}
