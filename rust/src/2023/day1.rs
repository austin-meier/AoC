use crate::helpers::load_day;


pub fn parse_nums(nums: &str) -> Vec<u32> {
    nums
        .chars()
        .filter_map(|c| c.to_digit(10))
        .collect()
}

pub fn join_edges(nums: &Vec<u32>) -> Option<u32> {
   Some(nums.first()?*10 + nums.last()?)
}

pub fn part1() -> u32 {
    let result: Vec<u32> = load_day(1)
        .iter()
        .map(|line| parse_nums(&line))
        .map(|line_nums| join_edges(&line_nums).expect("A line doesn't have any digits"))
        .collect();

    result.iter().sum()
}

pub fn convert_text_nums(str: &str) -> String {

    let mut result = str.to_string();

    let conversions = &[
        ("oneight", "18"),
        ("twone", "21"),
        ("threeight", "38"),
        ("fiveight", "58"),
        ("nineight", "98"),
        ("eightwo", "82"),
        ("eighthree", "83"),
        ("one", "1"),
        ("two", "2"),
        ("three", "3"),
        ("four", "4"),
        ("five", "5"),
        ("six", "6"),
        ("seven", "7"),
        ("eight", "8"),
        ("nine", "9"),
    ];

    for (str_val, num_val) in conversions {
        result = result.replace(str_val, num_val)
    }

    result
}

pub fn part2() -> u32 {
    let result: Vec<u32> = load_day(1)
        .iter()
        .map(|line| convert_text_nums(line))
        .map(|line| parse_nums(&line))
        .map(|line_nums| join_edges(&line_nums).expect("A line doesn't have any digits"))
        .collect();

    result.iter().sum()
}


#[cfg(test)]
mod tests {
    use crate::day1::{part1, parse_nums, join_edges, convert_text_nums};

    fn parse_part1_test_input() -> Vec<u32> {
        let input: Vec<String> = vec![
            "1abc2".into(),
            "pqr3stu8vwx".into(),
            "a1b2c3d4e5f".into(),
            "treb7uchet".into()
        ];

        let result: Vec<u32> = input
            .iter()
            .map(|line| parse_nums(line))
            .map(|line_nums| join_edges(&line_nums).expect("No line"))
            .collect();

        result
    }

    fn parse_part2_test_input() {
        let input: Vec<String> = vec![
            "two1nine".to_string(),
            "eightwothree".to_string(),
            "abcone2threexyz".to_string(),
            "xtwone3four".to_string(),
            "4nineeightseven2".to_string(),
            "zoneight234".to_string(),
            "7pqrstsixteen".to_string(),
        ];
    }
    
    #[test]
    fn day1_part1_parse_nums() {
        let expected = vec![12, 38, 15, 77];
        assert_eq!(expected, parse_part1_test_input());
    }

    #[test]
    fn day1_part2_string_digit_conv() {
        let input = "nineone";
        assert_eq!("91".to_string(), convert_text_nums(input))
    }

    #[test]
    fn day1_part1_sum() {
        let result = parse_part1_test_input();
        assert_eq!(142, result.iter().sum::<u32>());
    }

    #[test]
    fn day1_part1() {
        let result = 53080;
        assert_eq!(result, part1())
    }
}
