package com.example.HM.controller;

import com.example.HM.dto.MealResponseDTO;
import com.example.HM.model.Meal;
import com.example.HM.model.Reservation;
import com.example.HM.model.Restaurant;
import com.example.HM.repository.ReservationRepository;
import com.example.HM.repository.RestaurantRepository;
import com.example.HM.repository.MealRepository;
import com.example.HM.service.WeatherForecastService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.HM.service.MealService;
import com.example.HM.service.ReservationService;

@Controller
public class WebController {

    private final ReservationRepository reservationRepository;
    private final ReservationService reservationService;
    private final MealRepository mealRepository;
    private final WeatherForecastService weatherService;
    private final RestaurantRepository restaurantRepository;
    private final MealService mealService;
    private static final Logger logger = LoggerFactory.getLogger(WebController.class);


    public WebController(
            ReservationRepository reservationRepository,
            ReservationService reservationService,
            MealRepository mealRepository,
            WeatherForecastService weatherService,
            MealService mealService,
            RestaurantRepository restaurantRepository) {

        this.reservationRepository = reservationRepository;
        this.reservationService = reservationService;
        this.mealRepository = mealRepository;
        this.weatherService = weatherService;
        this.mealService = mealService;
        this.restaurantRepository = restaurantRepository;
    }

    // Página inicial com escolha de cliente ou staff
    @GetMapping("/")
    public String selecionarTipoUtilizador() {
        return "index";
    }

    // Página de cliente com restaurantes únicos
    @GetMapping("/restaurants")
    public String listarRestaurantes(Model model) {
        List<Restaurant> allRestaurants = restaurantRepository.findAll();
        List<Restaurant> distinctRestaurants = allRestaurants.stream()
                .distinct()
                .toList();

        model.addAttribute("restaurants", distinctRestaurants);
        return "homepage";
    }

    // Página de refeições por restaurante, agrupadas por data
    @GetMapping("/restaurant/{id}")
    public String verRefeicoesPorRestaurante(@PathVariable Long id, Model model) {
        Restaurant restaurante = restaurantRepository.findById(id).orElseThrow();
        List<Meal> meals = mealService.getMealsByRestaurant(id);
    
        Map<LocalDate, List<MealResponseDTO>> mealsPorData = meals.stream()
            .map(meal -> new MealResponseDTO(meal, weatherService.getForecast(meal.getDate())))
            .collect(Collectors.groupingBy(dto -> dto.getMeal().getDate(), TreeMap::new, Collectors.toList()));
    
        model.addAttribute("restaurant", restaurante);
        model.addAttribute("mealsPorData", mealsPorData);
        return "restaurant_details";
    }
    
    // Página de refeições (sem filtro de restaurante)
    @GetMapping("/meals")
    public String mostrarRefeicoes(Model model) {
        List<Meal> meals = mealService.getAllMeals();

        List<Meal> uniqueMeals = meals.stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.toMap(
                                m -> m.getName() + "|" + m.getDate() + "|" + m.getRestaurant().getName(),
                                m -> m,
                                (m1, m2) -> m1
                        ),
                        map -> new ArrayList<>(map.values())
                ));

        List<MealResponseDTO> mealsDTO = uniqueMeals.stream()
                .map(meal -> new MealResponseDTO(meal, weatherService.getForecast(meal.getDate())))
                .toList();

        model.addAttribute("meals", mealsDTO);
        return "meals";
    }

    @PostMapping("/reservar")
    public String reservar(@RequestParam Long mealId, RedirectAttributes redirectAttributes) {
        Meal meal = mealRepository.findById(mealId).orElseThrow();
        Reservation reservation = reservationRepository.save(new Reservation(meal));

        redirectAttributes.addFlashAttribute("mensagem", "Reserva confirmada com sucesso!");
        redirectAttributes.addFlashAttribute("token", reservation.getToken());

        return "redirect:/restaurant/" + meal.getRestaurant().getId();
    }

    @GetMapping("/admin")
    public String adminPage(Model model) {
        model.addAttribute("restaurant", new Restaurant());
        model.addAttribute("meal", new Meal());
        model.addAttribute("restaurants", restaurantRepository.findAll()); // para selecionar no formulário de refeição
        return "admin";
    }

    @PostMapping("/admin/add-restaurant")
    public String adicionarRestaurante(@ModelAttribute Restaurant restaurant, RedirectAttributes redirectAttributes) {
        restaurantRepository.save(restaurant);
        logger.info("Restaurante '{}' adicionado com sucesso.", restaurant.getName());
        redirectAttributes.addFlashAttribute("mensagemRestaurante", "Restaurante adicionado com sucesso!");
        return "redirect:/admin";
    }
    
    @PostMapping("/admin/add-meal")
    public String adicionarRefeicao(@RequestParam String name,
                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                    @RequestParam Long restaurantId,
                                    RedirectAttributes redirectAttributes) {
        Restaurant restaurante = restaurantRepository.findById(restaurantId).orElseThrow();
        mealRepository.save(new Meal(null, name, date, restaurante));
        logger.info("Refeição '{}' adicionada ao restaurante '{}' para o dia {}", name, restaurante.getName(), date);
        redirectAttributes.addFlashAttribute("mensagemRefeicao", "Refeição adicionada com sucesso!");
        return "redirect:/admin";
    }
    
    @PostMapping("/cancelar-reserva")
    public String cancelarReserva(@RequestParam String token, RedirectAttributes redirectAttributes) {
        boolean success = reservationService.cancelReservation(token);
        if (success) {
            redirectAttributes.addFlashAttribute("mensagem", "Reserva cancelada com sucesso.");
        } else {
            redirectAttributes.addFlashAttribute("mensagem", "Não foi possível cancelar a reserva.");
        }
        return "redirect:/meals"; // ou redireciona para a página anterior se quiseres
    }





    // Página de reservas (Staff)
    @GetMapping("/reservas")
    public String mostrarReservas(Model model) {
        model.addAttribute("reservas", reservationRepository.findAll());
        return "reservations";
    }

    @PostMapping("/checkin/{token}")
    public String fazerCheckin(@PathVariable String token) {
        Optional<Reservation> res = reservationRepository.findAll().stream()
                .filter(r -> r.getToken().equals(token))
                .findFirst();

        if (res.isPresent()) {
            reservationService.checkInReservation(token);
            logger.info("Check-in feito com sucesso para o token {}", token);
        } else {
            logger.warn("Tentativa de check-in com token inválido: {}", token);
        }

        return "redirect:/reservas";
    }


    @GetMapping("/staff")
    public String staffOptions() {
        return "staff_options";
    }

}
