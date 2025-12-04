use crate::helpers::load_day;
use regex::Regex;
use std::fs;

fn parse_nums(nums: &str) -> Vec<u32> {
    let digit_cap = Regex::new(r"(\d+)").unwrap();
    digit_cap.find_iter(nums).map(|m| m.as_str().parse::<u32>().unwrap()).collect()

}

fn double(n: u32) -> u32 {
    if n == 0 {return 1}
    n * 2
}

pub fn part1() -> u32 {
    let data = load_day(4);

    let scores: Vec<u32> = data.iter().map(|game| {
        let game_data = game.split(':').last().unwrap();
        let game_split: Vec<&str> = game_data.split('|').collect();
        let winning_nums = parse_nums(game_split.first().unwrap());
        let my_nums: Vec<u32> = parse_nums(game_split.last().unwrap());

        let winners: Vec<u32> = my_nums.iter().filter(|num| winning_nums.contains(num)).cloned().collect();
        let num_winners = winners.len();
        let mut score = 0;
        
        for _ in 0..num_winners {
            score = double(score);
        }

        
        score

    }).collect();  

    scores.iter().sum()

}

pub fn run() {
    let scratchcard_info = fs::read_to_string("resources/day4.txt").expect("Failed to get input!");
    let mut scratchcards = Vec::new();
    scratchcard_info
        .lines()
        .for_each(|line| scratchcards.push(Scratchcard::new(line)));

    p1(&scratchcards);
    p2(&scratchcards);
}

fn p1(scratchcards: &Vec<Scratchcard>) {
    let sum = scratchcards
        .iter()
        .fold(0, |acc, s| acc + s.compute_score());
    println!("Sum: {sum}");
}

fn p2(scratchcards: &Vec<Scratchcard>) {
    let mut instances = vec![1; scratchcards.len()];
    for (i, scratchcard) in scratchcards.iter().enumerate() {
        let score = scratchcard.compute_score();
        let num_winning_numbers = match score {
            0 => 0,
            _ => score.trailing_zeros() + 1,
        };

        for j in i + 1..=i + num_winning_numbers as usize {
            instances[j] += instances[i];
        }
    }

    let num_scratchcards: u32 = instances.iter().sum();

    println!("Total scratchcards: {num_scratchcards}");
}

fn parse_numbers(numbers: &str) -> Vec<u32> {
    let numbers: Vec<&str> = numbers.split_whitespace().into_iter().collect();
    numbers
        .iter()
        .map(|s| s.parse::<u32>().expect("Failed to parse number"))
        .collect()
}

struct Scratchcard {
    winning_numbers: Vec<u32>,
    owned_numbers: Vec<u32>,
}

impl Scratchcard {
    fn new(line: &str) -> Scratchcard {
        let (_, right) = line.split_once(':').unwrap();

        let (winning_numbers, owned_numbers) = right.split_once('|').unwrap();
        let winning_numbers = parse_numbers(winning_numbers);
        let owned_numbers = parse_numbers(owned_numbers);

        Scratchcard {
            winning_numbers,
            owned_numbers,
        }
    }

    fn compute_score(&self) -> u32 {
        self.owned_numbers.iter().fold(0, |acc, n| {
            if self.winning_numbers.contains(n) {
                match acc {
                    0 => 1,
                    _ => acc * 2,
                }
            } else {
                acc
            }
        })
    }
}


#[cfg(test)]
mod tests {
    #[test]
    fn get_winning_numbers() {
    }
}
