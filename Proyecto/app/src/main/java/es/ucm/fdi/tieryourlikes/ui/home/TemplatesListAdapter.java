package es.ucm.fdi.tieryourlikes.ui.home;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import es.ucm.fdi.tieryourlikes.R;
import es.ucm.fdi.tieryourlikes.model.Template;
import es.ucm.fdi.tieryourlikes.model.TierRow;

public class TemplatesListAdapter extends RecyclerView.Adapter<TemplatesListAdapter.ViewHolder> {

    private Activity activity;
    private List<Template> list;
    private OnItemClickListener onItemClickListener;

    public TemplatesListAdapter(Activity activity, List<Template> list, OnItemClickListener onItemClickListener){
        this.activity = activity;
        this.list = list;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public TemplatesListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.templates_home_item_fragment, parent, false);

        return new TemplatesListAdapter.ViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TemplatesListAdapter.ViewHolder holder, int position) {
        Template template = list.get(position);
        holder.templateName.setText(template.getTitle());
        holder.imageTemplate.setImageResource(R.drawable.ic_baseline_save_24);
        //Glide.with(activity).load(template.getCover_image()).into(holder.imageTemplate);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private OnItemClickListener onItemClickListener;
        private TextView templateName;
        private ImageView imageTemplate;

        public ViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            templateName = itemView.findViewById(R.id.home_item_template_text_view);
            imageTemplate = itemView.findViewById(R.id.home_item_template_image_view);
            this.onItemClickListener = onItemClickListener;
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClickListener(getAdapterPosition(), list);
        }
    }


    public interface OnItemClickListener {
        void onItemClickListener(int position, List<Template> list);
    }
}
