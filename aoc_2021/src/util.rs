pub fn read_input_file(path: &str) -> String {
    return std::fs::read_to_string(path).expect("Error reading input file.")
}