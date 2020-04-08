package com.exemple.pillsreminder.relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.exemple.pillsreminder.drug.Drug;
import com.exemple.pillsreminder.pill.Pill;

import java.util.ArrayList;
import java.util.List;

public class DrugWithPills {

    @Embedded public Drug drug;
    @Relation(
            parentColumn = "drug_id",
            entity = Pill.class,
            entityColumn = "drugType_id"
    )
    public List<Pill> pill = new ArrayList<>();
}
