use std::fs;

pub fn load_day(day: u8) -> Vec<String> {
    let file_path = "day".to_string() + &day.to_string() + ".txt";
    load_resource_file_lines(&file_path)
}

pub fn load_resource_file_lines(file_path: &str) -> Vec<String> {
    let resources_folder = String::from("resources/");
    let file_path = resources_folder + file_path; 
    let content = fs::read_to_string(file_path).expect("Could not find resource file");
    content.lines().map(String::from).collect()
}

#[cfg(test)]
mod tests {
    use crate::helpers::load_day;

    #[test]
    fn load_test_file() {
        let content = load_day(99);
        let result: Vec<String> = vec!["load_day".into(), "test".into()];
        assert_eq!(result, content);
    }
}
