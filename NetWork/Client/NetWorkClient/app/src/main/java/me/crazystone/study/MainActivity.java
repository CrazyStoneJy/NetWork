package me.crazystone.study;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.crazystone.study.okhttp.NetCallBack;
import me.crazystone.study.okhttp.OKHttps;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {

    private static final String HOST = "http://10.6.9.195:8081/";
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.main_recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new MainAdapter(getList()));

    }

    private List<String> getList() {
        List<String> list = new ArrayList<>();
        list.add("普通get请求");
        list.add("带参数的get请求");
        list.add("参数为数组的get请求");
        list.add("普通post请求");
        list.add("上传普通文件");
        list.add("上传图片");
        list.add("下载文件");
        list.add("带进度条的上传文件");
        list.add("带进度条的下载文件");
        list.add("多线程下载文件");
        list.add("多线程上传文件");
        return list;
    }

    class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {

        private List<String> list;

        public MainAdapter(List<String> list) {
            this.list = list;
        }

        @Override
        public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View contentView = View.inflate(getApplicationContext(), android.R.layout.simple_list_item_1, null);
            return new MainViewHolder(contentView);
        }

        @Override
        public void onBindViewHolder(MainViewHolder holder, int position) {
            holder.txt.setText(list.get(position));
            holder.txt.setOnClickListener(v -> {
                switch (position) {
                    case 0:
                        OKHttps.getInstance().get(HOST + "v1/getTest").listen(new NetCallBack<String>() {
                            @Override
                            protected String parseResponse(int statusCode, ResponseBody responseBody, String url) {
                                try {
                                    String resString = responseBody.string();
                                    Log.d("TAG", "resString:" + resString);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                return null;
                            }
                        });
                        break;
                    case 1:

                        break;
                }
            });
        }

        @Override
        public int getItemCount() {
            return list != null ? list.size() : 0;
        }

        class MainViewHolder extends RecyclerView.ViewHolder {

            TextView txt;

            public MainViewHolder(View itemView) {
                super(itemView);
                txt = (TextView) itemView.findViewById(android.R.id.text1);
            }
        }
    }


}
