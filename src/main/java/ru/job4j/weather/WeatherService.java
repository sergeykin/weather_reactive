package ru.job4j.weather;


import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class WeatherService {

    private final Map<Integer, Weather> weathers = new ConcurrentHashMap<>();

    {
        weathers.put(1, new Weather(1, "Msc", 20));
        weathers.put(2, new Weather(2, "SPb", 15));
        weathers.put(3, new Weather(3, "Bryansk", 15));
        weathers.put(4, new Weather(4, "Smolensk", 15));
        weathers.put(5, new Weather(5, "Kiev", 15));
        weathers.put(6, new Weather(6, "Minsk", 15));
        weathers.put(7, new Weather(7, "GdeTo", 20));
    }

    public Mono<Weather> findById(Integer id) {
        return Mono.justOrEmpty(weathers.get(id));
    }

    public Flux<Weather> all() {
        return Flux.fromIterable(weathers.values());
    }

    public Flux<Weather> hottest() {
        int maxtemp = weathers.values().stream().mapToInt(Weather::getTemperature).max().getAsInt();
        return Flux.fromStream(weathers.values().stream().filter(x -> x.getTemperature() == maxtemp));
    }

    public Flux<Weather> cityGreatThen(int id) {
        return Flux.fromStream(weathers.values().stream().filter(x -> x.getTemperature() > id));
    }

}
