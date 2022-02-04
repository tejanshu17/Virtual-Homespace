package com.example.login2

import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.login2.Model.model
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.ar.core.Anchor
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.collision.Box
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.rendering.ViewRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode
import kotlinx.android.synthetic.main.augmented_main.*
import java.util.concurrent.CompletableFuture

private const val PEEK_HEIGHT = 50f
class augmented : AppCompatActivity() {
/*
    lateinit var arFragment: ArFragment

    private val models = mutableListOf(
        model(R.drawable.ballchair,"ballchair",R.raw.ballchair),
        model(R.drawable.conferencechair,"conference chair",R.raw.conferencechair),
        model(R.drawable.couchchair,"ballchair",R.raw.couchchair),
        model(R.drawable.durianofficechair,"office chair",R.raw.durianofficechair),
        model(R.drawable.gamingchair,"gaming chair",R.raw.gamingchair),
        model(R.drawable.gapchair,"gap chair",R.raw.gapchair),
        model(R.drawable.redchairmueblescasa,"red chair mues bles casa",R.raw.redchairmueblescasa),
        model(R.drawable.modernarmchairyafaray,"modern arm chair",R.raw.modernarmchairyafaray),
        model(R.drawable.officeergonomicchairs,"office chair",R.raw.officeergonomicchairs),
        model(R.drawable.vikchair,"regular chair",R.raw.vikchair),
        model(R.drawable.bed,"bed",R.raw.bed),
        model(R.drawable.bednewer,"queen size bed",R.raw.bednewer),
        model(R.drawable.bednewest,"king size bed",R.raw.bednewest),
        model(R.drawable.blackdoublebed,"double bed",R.raw.blackdoublebed),
        model(R.drawable.bunk,"bunk bed",R.raw.bunk),
        model(R.drawable.doublebed,"double bed",R.raw.doublebed),
        model(R.drawable.kasurbedwithsidetable,"bed with side table",R.raw.kasurbedwithsidetable),
        model(R.drawable.militarybed,"hostel bed",R.raw.militarybed),
        model(R.drawable.poltronadoublebed,"double bed",R.raw.poltronadoublebed),
        model(R.drawable.princessbed,"double bed",R.raw.princessbed),
        model(R.drawable.castortable,"castor table",R.raw.castortable),
        model(R.drawable.coffeetable,"coffee table",R.raw.coffeetable),
        model(R.drawable.diningrect,"rectangle simple table",R.raw.diningrect),
        model(R.drawable.majbootdiningtable,"dining table",R.raw.majbootdiningtable),
        model(R.drawable.rounddining,"round dining table",R.raw.rounddining),
        model(R.drawable.roundstylecutcoffee," coffee table",R.raw.roundstylecutcoffee),
        model(R.drawable.styledining," traditional design table",R.raw.styledining),
        model(R.drawable.styletable," coffee table",R.raw.styletable),
        model(R.drawable.whitediningtable," dining table",R.raw.whitediningtable),
        model(R.drawable.yafaraydiningset," dining set",R.raw.yafaraydiningset),
        model(R.drawable.candle," candle",R.raw.candle),
        model(R.drawable.discoball," home discoball",R.raw.discoball),
        model(R.drawable.fernstand," fernstand",R.raw.fernstand),
        model(R.drawable.hanger,"hanger",R.raw.hanger),
        model(R.drawable.lamp,"lamp",R.raw.lamp),
        model(R.drawable.livinglamp,"lamp",R.raw.livinglamp),
        model(R.drawable.mask,"mask",R.raw.mask),
        model(R.drawable.potblack,"black pot",R.raw.potblack),
        model(R.drawable.potwhite,"white pot",R.raw.potwhite),
        model(R.drawable.smilepiece,"ash tray",R.raw.smilepiece),
        model(R.drawable.tlight,"lamp",R.raw.tlight),
        model(R.drawable.beigesofa,"beige sofa",R.raw.beigesofa),
        model(R.drawable.finalclearsofa," sofa",R.raw.finalclearsofa),
        model(R.drawable.finalsimplesofa," sofa set",R.raw.finalsimplesofa),
        model(R.drawable.idkbeige," sofa set",R.raw.idkbeige),
        model(R.drawable.ikeaps," sofa set",R.raw.ikeaps),
        model(R.drawable.leathersofa," leather sofa ",R.raw.leathersofa),
        model(R.drawable.redajeebsofa," club sofa ",R.raw.redajeebsofa),
        model(R.drawable.sofa," sofa ",R.raw.sofa),
        model(R.drawable.spacecouch," space couch ",R.raw.spacecouch),
        model(R.drawable.usofa," u-shaped sofa ",R.raw.usofa),
        model(R.drawable.whiteajeebsofa," club sofa ",R.raw.whiteajeebsofa),
        model(R.drawable.yafa," two seat sofa ",R.raw.yafa),
        model(R.drawable.barcycle," bar cycle stool ",R.raw.barcycle),
        model(R.drawable.barset," home mini bar set ",R.raw.barset),
        model(R.drawable.barstool," home bar stool ",R.raw.barstool),
        model(R.drawable.frelstool," frel stool ",R.raw.frelstool),
        model(R.drawable.kitchenmini," kitchen stool ",R.raw.kitchenmini),
        model(R.drawable.kitstool," kitchen stool ",R.raw.kitstool),
        model(R.drawable.stool," stool ",R.raw.stool),
        model(R.drawable.stoolwood," wooden stool ",R.raw.stoolwood),
        model(R.drawable.taburetka," stool ",R.raw.taburetka),
        model(R.drawable.redsteel," steel stool ",R.raw.redsteel),
        model(R.drawable.blackstudysteel," study table ",R.raw.blackstudysteel),
        model(R.drawable.desk," desk ",R.raw.desk),
        model(R.drawable.gamingdesk," gaming desk ",R.raw.gamingdesk),
        model(R.drawable.kidsdesk," children's desk ",R.raw.kidsdesk),
        model(R.drawable.metalglasstable," glass table ",R.raw.metalglasstable),
        model(R.drawable.padhaitable," study table ",R.raw.padhaitable),
        model(R.drawable.pctable," study table unit ",R.raw.pctable),
        model(R.drawable.studyhalf," study unit ",R.raw.studyhalf),
        model(R.drawable.studysteel," office table ",R.raw.studysteel),
        model(R.drawable.woodenofficetable," wooden office desk ",R.raw.woodenofficetable),
        model(R.drawable.barstable," bar table ",R.raw.barstable),
        model(R.drawable.concretetable," rectangle table ",R.raw.concretetable),
        model(R.drawable.ctable," plain table ",R.raw.ctable),
        model(R.drawable.livingroomtable," living room table ",R.raw.livingroomtable),
        model(R.drawable.revolvingtable," ground revolving table ",R.raw.revolvingtable),
        model(R.drawable.scarytable," entrance table ",R.raw.scarytable),
        model(R.drawable.tablered," living room table ",R.raw.tablered),
        model(R.drawable.tablewood," living room table ",R.raw.tablewood),
        model(R.drawable.white," room table ",R.raw.white),
        model(R.drawable.whitelevels,"living room table ",R.raw.whitelevels),
        model(R.drawable.teletable,"television table ",R.raw.teletable),
        model(R.drawable.tvred,"television table ",R.raw.tvred),
        model(R.drawable.tvsimple,"television table ",R.raw.tvsimple),
        model(R.drawable.tvunit,"television unit ",R.raw.tvunit),
        model(R.drawable.tvunitbig,"television unit ",R.raw.tvunitbig),
        model(R.drawable.tvunitnew,"television unit ",R.raw.tvunitnew),
        model(R.drawable.tvunitsmall,"television unit ",R.raw.tvunitsmall),
        model(R.drawable.tvunitlast,"television unit ",R.raw.tvunitlast),
       // model(R.drawable.stove,"stove ",R.raw.stove),
        model(R.drawable.changing,"mini wardrobe ",R.raw.changing),
        model(R.drawable.fourdoor,"four door wardrobe",R.raw.fourdoor),
        model(R.drawable.coronawardrobe,"wardrobe set ",R.raw.coronawardrobe),
        model(R.drawable.doorsingle,"single door wardrobe ",R.raw.doorsingle),
        model(R.drawable.doortriple,"double door wardrobe ",R.raw.doortriple),
        model(R.drawable.drawerhalf,"drawer unit ",R.raw.drawerhalf),
        model(R.drawable.meuble,"show unit ",R.raw.meuble),
        model(R.drawable.sidehalf,"mini drawer ",R.raw.sidehalf),
        model(R.drawable.walking,"walk-in wardrobe ",R.raw.walking),
        model(R.drawable.wardrobecut,"closet ",R.raw.wardrobecut),
        model(R.drawable.whiteopen,"open closet ",R.raw.whiteopen)
        )

    lateinit var selectedModel: model

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.augmented_main)

    /* val anotheract = findViewById<FloatingActionButton>(R.id.anotheract)
        anotheract.setOnClickListener {

            //val intent = Intent(this, SecondActivity::class.java)
            //startActivity(intent)

            val manager = supportFragmentManager
            val transaction = manager.beginTransaction()
            //transaction.replace(R.id.wishlistFragment, .getInstance())
            //transaction.commit()
            //Fragment_account fragment = new Fragment_account();
            //Fragment_account fragment = new Fragment_account();
            val fragment_wishlist = Fragment_wishlist(this)
            this.supportFragmentManager.beginTransaction().replace(R.id.Fragmentcontainer, fragment_wishlist).addToBackStack(null).commit()

        }*/
        arFragment = fragment as ArFragment
        setupBottomSheet()
        setupRecyclerView()
        setupDoubleTapArPlaneListener()

    }

    private fun setupDoubleTapArPlaneListener(){
        arFragment.setOnTapArPlaneListener{ hitResult, _, _ ->
            loadModel { modelRenderable, viewRenderable ->

                addNodeToScene(hitResult.createAnchor(),modelRenderable,viewRenderable)
            }
            
        }
    }
    private fun setupRecyclerView(){

recyclermodel.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        recyclermodel.adapter = ModelAdapter(models).apply {
            selectedModel.observe(this@augmented, Observer {
                this@augmented.selectedModel = it
                val newTitle = "Models:(${it.title})"
                textmodel.text = newTitle
            })
        }
    }

    private fun setupBottomSheet()
    {
        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.peekHeight =
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                PEEK_HEIGHT,
                resources.displayMetrics

            ).toInt()
bottomSheetBehavior.addBottomSheetCallback(object :
BottomSheetBehavior.BottomSheetCallback(){
    override fun onSlide(bottomSheet: View, slideOffset: Float) {
        bottomSheet.bringToFront()
    }

    override fun onStateChanged(bottomSheet: View, newState: Int) {
    }
})
    }

    private fun getCurrentScene() = arFragment.arSceneView.scene

    private fun createDeleteButton(): Button {
        return Button(this).apply {
            text = "Delete"
            setBackgroundColor(Color.RED)
            setTextColor(Color.WHITE)
        }

    }


    private fun addNodeToScene(anchor: Anchor,
                               modelRenderable: ModelRenderable,
                               viewRenderable: ViewRenderable){

        val anchorNode = AnchorNode(anchor)
        val modelNode = TransformableNode(arFragment.transformationSystem).apply {
            renderable = modelRenderable
            setParent(anchorNode)
            getCurrentScene().addChild(anchorNode)
            select()
        }
       val viewNode = Node().apply {
           renderable = null
           setParent(modelNode)
           val box = modelNode.renderable?.collisionShape as Box
           localPosition = Vector3(0f,box.size.y,0f)
           (viewRenderable.view as Button).setOnClickListener{
               getCurrentScene().removeChild(anchorNode)
           }
       }

        modelNode.setOnTapListener { _, _ ->
                if(!modelNode.isTransforming){
                    if(viewNode.renderable == null){

                        viewNode.renderable = viewRenderable
                    } else
                    {
                        viewNode.renderable = null

                    }
                }



        }
    }




 private fun loadModel(callback:(ModelRenderable, ViewRenderable)-> Unit){

     val modelRenderable = ModelRenderable.builder()
         .setSource(this,selectedModel.modelResourceId)
         .build()

     val viewRenderable = ViewRenderable.builder()
         .setView(this, createDeleteButton())
         .build()
     CompletableFuture.allOf(modelRenderable,viewRenderable)
         .thenAccept{
             callback(modelRenderable.get(),viewRenderable.get())
         }
         .exceptionally {
             Toast.makeText(this,"error loading model",Toast.LENGTH_LONG).show()
             null
         }
 }



*/
}
