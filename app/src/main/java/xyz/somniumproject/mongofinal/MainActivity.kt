package xyz.somniumproject.mongofinal

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TabHost
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

    }
}
