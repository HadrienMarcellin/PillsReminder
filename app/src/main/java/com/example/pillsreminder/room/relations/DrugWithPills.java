package com.example.pillsreminder.room.relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.pillsreminder.room.drug.Drug;
import com.example.pillsreminder.room.pill.Pill;

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
