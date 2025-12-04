use crate::helpers::load_day;

#[derive(Debug, PartialEq, Eq, PartialOrd, Ord)]
struct ColorTotal {
    red: u32,
    green: u32,
    blue: u32,
}

pub fn sum_valid_games(games: Vec<String>) -> u32 {
    let valid_game = ColorTotal{
        red: 12,
        green: 13,
        blue: 14
    };

    let games: Vec<u32> = games.iter().map(|line| {
        let data: Vec<&str> = line.split(':').collect();
        let game_id: u32 = data.first().unwrap().split(' ').collect::<Vec<&str>>().last().unwrap().parse().unwrap();
        let game_data = data.last().expect("No game data split");
        
        let game_colors: Vec<ColorTotal> = game_data.split(';').map(|game| {
            let mut total = ColorTotal{red: 0, green: 0, blue: 0};
            for draw in game.split(',') {
                let draw_data: Vec<&str> = draw.trim().split(' ').collect();
                let color_count: u32 = draw_data.first().unwrap().parse().unwrap();
                if let Some(color) = draw_data.last() {
                    match color {
                        &"red" => total.red += color_count,
                        &"green" => total.green += color_count,
                        &"blue" => total.blue += color_count,
                        _ => panic!("I have encountered a color I don't know")
                    }
                }
            }
            total
        }).collect();


        for game in game_colors {
            if game.red > valid_game.red || game.green > valid_game.green || game.blue > valid_game.blue {
                return 0;
            }
        }
        game_id

    }).collect();

    games.iter().sum()

}


pub fn cube_min_games(games: Vec<String>) -> u32 {

    let games: Vec<u32> = games.iter().map(|line| {
        let mut min_game = ColorTotal{
            red: 0,
            green: 0,
            blue: 0
        };
        let data: Vec<&str> = line.split(':').collect();
        let game_data = data.last().expect("No game data split");
        
        for game in game_data.split(';') {
            for draw in game.split(',') {
                let draw_data: Vec<&str> = draw.trim().split(' ').collect();
                let color_count: u32 = draw_data.first().unwrap().parse().unwrap();
                if let Some(color) = draw_data.last() {
                    match color {
                        &"red" => min_game.red = color_count.max(min_game.red),
                        &"green" => min_game.green = color_count.max(min_game.green),
                        &"blue" => min_game.blue = color_count.max(min_game.blue),
                        _ => panic!("I have encountered a color I don't know")
                    }
                }
            }
        }

        min_game.red * min_game.green * min_game.blue


    }).collect();

    games.iter().sum()

}


pub fn part1() -> u32 {
    
    let content = load_day(2);

    sum_valid_games(content)
    
}

pub fn part2() -> u32 {
    
    let content = load_day(2);

    cube_min_games(content)
    
}

#[cfg(test)]
mod tests {
    use crate::{helpers::load_resource_file_lines, day2::{sum_valid_games, cube_min_games}};


    #[test]
    fn part1() {
        let test_data = load_resource_file_lines("day2part1.txt");
        
        assert_eq!(8, sum_valid_games(test_data));
    }

    #[test]
    fn part2() {
        let test_data = load_resource_file_lines("day2part1.txt");
        
        assert_eq!(2286, cube_min_games(test_data));
    }
}
