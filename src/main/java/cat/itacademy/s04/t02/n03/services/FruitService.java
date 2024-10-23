package cat.itacademy.s04.t02.n03.services;

import cat.itacademy.s04.t02.n03.exception.FruitNotFoundException;
import cat.itacademy.s04.t02.n03.model.Fruit;
import cat.itacademy.s04.t02.n03.repository.FruitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FruitService {
    
    @Autowired
    private FruitRepository fruitRepository;
    
    public Fruit createFruit(Fruit fruit) {
        fruitRepository.findById(fruit.getId())
                .ifPresent(existingFruit -> {
                    throw new FruitNotFoundException("Fruit already exists with id: " + fruit.getId());
                });
        return fruitRepository.save(fruit);
    }
    
    public List<Fruit> getAllFruits() {
        fruitRepository.findAll().stream()
                .findAny()
                .orElseThrow(() -> new FruitNotFoundException("No fruits in DB"));
        return fruitRepository.findAll();
    }
    
    public Fruit getFruitById(int id) {
        return fruitRepository.findById(id).orElseThrow(() -> new FruitNotFoundException("Fruit not found with id: " + id));
    }
    
    public Fruit updateFruit(int id, Fruit fruit) {
        return fruitRepository.findById(id)
                .map(existingFruit -> {
                    existingFruit.setName(fruit.getName());
                    existingFruit.setWeight(fruit.getWeight());
                    return fruitRepository.save(existingFruit);  // Guarda los cambios
                })
                .orElseThrow(() -> new FruitNotFoundException("Fruit not found with id: " + id));
    }
    
    public void deleteFruit(int id) {
        fruitRepository.findById(id)
                .orElseThrow(() -> new FruitNotFoundException("Fruit not found with id: " + id));
        fruitRepository.deleteById(id);
    }
    
}
