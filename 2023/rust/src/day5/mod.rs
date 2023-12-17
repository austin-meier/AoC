use crate::helpers::{load_resource_file_lines, load_day};
use std::collections::HashMap;

pub fn create_remaps(overrides: Vec<u32>) -> HashMap<u32, u32> {

    let mut result: HashMap<u32, u32> = HashMap::new();
    for e in overrides.chunks(3) {
        let (dest, src, length) = (e[0], e[1], e[2]);

        for i in 0..length {
            result.insert(src+i, dest+i);
        }
        
    }
    result
}

pub fn part1() {
    // let data = load_resource_file_lines("day5part1.txt");
    let data = load_day(5);

    let mut seeds: Vec<u32> = vec![];
    let mut seeds_to_soil: Vec<u32> = vec![];
    let mut soil_to_fert: Vec<u32> = vec![];
    let mut fert_to_water: Vec<u32> = vec![];
    let mut water_to_light: Vec<u32> = vec![];
    let mut light_to_temp: Vec<u32> = vec![];
    let mut temp_to_humidity: Vec<u32> = vec![];
    let mut humidity_to_loc: Vec<u32> = vec![];

    let mut current = &mut seeds;

    for line in data {
        
        if line.contains("seed-to-soil") {
            current = &mut seeds_to_soil;
        } else if line.contains("soil-to-fertilizer") {
            current = &mut soil_to_fert;
        } else if line.contains("fertilizer-to-water") {
            current = &mut fert_to_water;
        } else if line.contains("water-to-light") {
            current = &mut water_to_light;
        } else if line.contains("light-to-temperature") {
            current = &mut light_to_temp;
        } else if line.contains("temperature-to-humidity") {
            current = &mut temp_to_humidity;
        } else if line.contains("humidity-to-location") {
            current = &mut humidity_to_loc;
        } else {
            current.extend(
                line.split_whitespace()
                    .into_iter()
                    .filter_map(|num| num.parse::<u32>().ok())
            );
        }
    }

    let mut remaps: Vec<HashMap<u32, u32>> = Vec::new();
    remaps.push(create_remaps(seeds_to_soil));
    remaps.push(create_remaps(soil_to_fert));
    remaps.push(create_remaps(fert_to_water));
    remaps.push(create_remaps(water_to_light));
    remaps.push(create_remaps(light_to_temp));
    remaps.push(create_remaps(temp_to_humidity));
    remaps.push(create_remaps(humidity_to_loc));



    let result = seeds.iter().map(|seed| {
        let mut x = seed;

        for map in &remaps {
            x = match map.get(x) {
                Some(n) => n,
                None => x,
            }
        }

        x
    }).min().unwrap();
    
    println!("part 1: {result:?}");
}
