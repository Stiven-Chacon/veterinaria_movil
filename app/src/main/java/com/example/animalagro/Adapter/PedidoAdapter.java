package com.example.animalagro.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.animalagro.R;
import com.example.animalagro.data.Pedido;
import java.util.List;

public class PedidoAdapter  extends RecyclerView.Adapter<PedidoAdapter.PedidoViewHolder> {

    private List<Pedido> pedidos;
    private Context context;

    public PedidoAdapter(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    @NonNull
    @Override
    public PedidoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.viewholder_pedidos_list, parent, false);
        return new PedidoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PedidoViewHolder holder, int position) {
        Pedido pedido = pedidos.get(position);

        holder.textViewFecha.setText("Fecha: " + pedido.getPeFecha());
        holder.textViewEstado.setText("Estado: " + pedido.getPeEstado());
        holder.textViewTotal.setText("Valor: $" + pedido.getPeTotalPedido());

        // Configurar el botón para abrir la URL del PDF
        holder.buttonVerPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urlPdfPedido = pedido.getUrlPdfPedido();
                if (urlPdfPedido != null && !urlPdfPedido.isEmpty()) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlPdfPedido));
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return pedidos.size();
    }

    public class PedidoViewHolder extends RecyclerView.ViewHolder {
        TextView textViewFecha;
        TextView textViewEstado;
        TextView textViewTotal;
        Button buttonVerPDF;

        public PedidoViewHolder(View itemView) {
            super(itemView);
            textViewFecha = itemView.findViewById(R.id.txtFechaPedido);
            textViewEstado = itemView.findViewById(R.id.txtEstadoPedido);
            textViewTotal = itemView.findViewById(R.id.txtTotalPedido);
            buttonVerPDF = itemView.findViewById(R.id.btnPDFProducto);
        }
    }
}