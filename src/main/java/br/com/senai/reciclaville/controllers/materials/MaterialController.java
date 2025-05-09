package br.com.senai.reciclaville.controllers.materials;

import br.com.senai.reciclaville.models.entities.Materials;
import br.com.senai.reciclaville.services.materials.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/materials")
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    @PostMapping
    public ResponseEntity<Materials> create(@RequestBody Materials material) throws Exception{
        this.materialService.newMaterial(material);
        return ResponseEntity.ok(material);
    }

    @GetMapping
    public ResponseEntity<List<Materials>> findAllMaterials(){
        List<Materials> materials = this.materialService.findAllMaterials();
        return materials.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(materials);
    }

    @GetMapping("/id")
    public ResponseEntity<Materials> findMaterial(@PathVariable Long id){
        Optional<Materials> material = materialService.findMaterialById(id);
        return material.map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.notFound().build());
    }

    @PutMapping("/id")
    public ResponseEntity<Materials> update(@PathVariable Long id, @RequestBody Materials materials) throws Exception{
        Materials updateMaterial = this.materialService.updateMaterial(id, materials);
        return ResponseEntity.ok(updateMaterial);
    }

    @DeleteMapping("/id")
    public ResponseEntity<String> deleteMaterial(@PathVariable Long id){
        this.materialService.deleteMaterial(id);
        return ResponseEntity.ok("Material deletado com sucesso!");
    }
}
