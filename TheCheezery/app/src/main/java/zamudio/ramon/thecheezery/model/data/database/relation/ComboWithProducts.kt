package zamudio.ramon.thecheezery.model.data.database.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import zamudio.ramon.thecheezery.model.data.database.entity.ComboEntity
import zamudio.ramon.thecheezery.model.data.database.entity.ProductComboEntity
import zamudio.ramon.thecheezery.model.data.database.entity.ProductEntity

data class ComboWithProducts(
    @Embedded
    val combo: ComboEntity,

    @Relation(
        parentColumn = "idCombo",
        entityColumn = "idProduct",
        associateBy = Junction(
            value = ProductComboEntity::class,
            parentColumn = "idCombo",
            entityColumn = "idProduct"
        )
    )
    val products: List<ProductEntity>
)
