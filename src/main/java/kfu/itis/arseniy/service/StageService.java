package kfu.itis.arseniy.service;

import kfu.itis.arseniy.entity.Stage;
import kfu.itis.arseniy.repository.StageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StageService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    private final StageRepository stageRepository;

    public List<Stage> findAll() {
        logger.info("Получение всех этапов");
        return stageRepository.findAll();
    }

    public Stage findById(Long id) {
        logger.info("Поиск этапа по ID: {}", id);
        return stageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stage not found"));
    }

    public Optional<Stage> findByName(String name) {
        logger.info("Поиск этапа по имени: {}", name);
        return stageRepository.findByName(name);
    }

    public Stage createStage(String name, String description) {
        Stage stage = new Stage(name, description);
        Stage savedStage = stageRepository.save(stage);
        logger.info("Этап создан с ID: {}", savedStage.getId());
        return savedStage;

    }

    public boolean existsByName(String name) {
        logger.info("Проверка существования этапа с именем: {}", name);
        return stageRepository.existsByName(name);
    }


    public List<Stage> findAllSorted() {
        logger.info("Получение всех этапов с сортировкой по имени");
        return stageRepository.findAllByOrderByNameAsc();
    }

    public Stage getInitialStage() {
        logger.info("Получение начального этапа 'Создан'");
        return stageRepository.findByName("Создан")
                .orElseThrow(() -> new RuntimeException("Стадия заказа 'создан' не найдена"));
    }
}