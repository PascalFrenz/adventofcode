pub fn read_input_file(path: &str) -> Result<String, bool> {
    return std::fs::read_to_string(path).map_err(|_| true);
}