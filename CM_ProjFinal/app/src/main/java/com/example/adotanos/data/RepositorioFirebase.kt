package com.example.adotanos.data

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

object RepositorioFirebase {
    private val database = FirebaseDatabase.getInstance().getReference("animais")

    fun addAnimal(animal: Animal) {
        val id = database.push().key!!
        database.child(id).setValue(animal.copy(id=id))
    }

    fun updateAnimal(animal:Animal) {
        database.child(animal.id).setValue(animal)
    }

    fun deleteAnimal(id:String) {
        database.child(id).removeValue()
    }

    fun getAnimals(onDataChange: (List<Animal>) -> Unit) {
        database.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val animalList = mutableListOf<Animal>()
                snapshot.children.forEach {
                    it.getValue(Animal::class.java)?.let {animal -> animalList.add(animal)}
                }
                onDataChange(animalList)
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }
}