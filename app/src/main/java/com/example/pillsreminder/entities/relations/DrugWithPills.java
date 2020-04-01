package com.example.pillsreminder.entities.relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.pillsreminder.entities.Drug;
import com.example.pillsreminder.entities.Pill;

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
