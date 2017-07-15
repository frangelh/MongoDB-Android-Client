package xyz.somniumproject.mongofinal

import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.TabHost
import android.widget.Toast
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.result.Result
import com.github.kittinunf.result.getAs
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tab_ordenes.setup()
        var spec: TabHost.TabSpec = tab_ordenes.newTabSpec("tag1")
        spec.setContent(R.id.tab_orden1)
        spec.setIndicator("Movimientos de inventario")
        tab_ordenes.addTab(spec)
        spec = tab_ordenes.newTabSpec("tag2")
        spec.setContent(R.id.tab_orden2)
        spec.setIndicator("Pedidos")
        tab_ordenes.addTab(spec)
        spec = tab_ordenes.newTabSpec("tag3")
        spec.setContent(R.id.tab_orden3)
        spec.setIndicator("Existencia")
        tab_ordenes.addTab(spec)

        //llamando metodo para lo relazionado a movimiento
        moduloMovimiento()


    }

    fun moduloMovimiento() {


        val adapterTipoMovimiento = ArrayAdapter(baseContext, android.R.layout.simple_spinner_item, listOf("ENTRADA", "SALIDA"))
        adapterTipoMovimiento.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_movimiento_tipo.adapter = adapterTipoMovimiento


        btn_movimiento_cancelar.setOnClickListener {
            limpiarMovimiento()
        }
        btn_movimiento_procesar.setOnClickListener {
            if (txt_movimiento_codigo.text.isNotEmpty() &&
                    txt_movimiento_articulo.text.isNotEmpty() &&
                    txt_movimiento_cantidad.text.isNotEmpty()) {


                val progressDialog = ProgressDialog(this@MainActivity)
                progressDialog.setMessage("Procesando peticion")
                progressDialog.setCancelable(false)
                progressDialog.progress = 0
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
                progressDialog.show()

                val parametros = listOf(
                        "codigoMovimiento" to txt_movimiento_codigo.text.toString(),
                        "tipoMovimiento" to spinner_movimiento_tipo.selectedItem.toString(),
                        "codigoArticulo" to txt_movimiento_articulo.text.toString().toLong(),
                        "cantidad" to txt_movimiento_cantidad.text.toString().toLong()

                )
                Fuel.get("http://deamon3.ddns.net:8888/rest/procesarMovimiento/", parametros).responseObject(TramaMovil.Deserializer()) { _, _, result ->
                    when (result) {
                        is Result.Failure -> println(result)
                        is Result.Success -> {
                            val retorno: TramaMovil? = result.getAs()
                            if (retorno != null) {
                                Toast.makeText(baseContext, retorno.mensaje, Toast.LENGTH_LONG).show()
                                limpiarMovimiento()
                            }
                        }
                    }
                    progressDialog.dismiss()
                }

            } else {
                Toast.makeText(baseContext, "Los campos no pueden ser vacios...", Toast.LENGTH_LONG).show()

            }

        }
    }

    fun limpiarMovimiento() {
        txt_movimiento_articulo.text.clear()
        txt_movimiento_cantidad.text.clear()
        txt_movimiento_codigo.text.clear()
        spinner_movimiento_tipo.setSelection(0)
    }
}
