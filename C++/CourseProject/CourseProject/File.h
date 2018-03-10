#pragma once

#include <fstream>
#include <vector>
#include <string>

template <class T> class File
{
public:
	std::vector<T> get_all(const std::string& filename)
	{
		std::vector<T> items;

		std::ifstream file(filename, std::ios::binary | std::ios::in | std::ios::out);

		if (!file) throw FileException("Cannot open file for reading");

		file.seekg(0, std::ios::end);
		auto file_size = (size_t)file.tellg();
		file.seekg(0, std::ios::beg);

		if (file_size == 0)
			return items;

		items.resize(file_size / sizeof(T));
		file.read(reinterpret_cast<char *>(&items[0]), file_size);
		return items;
	}

	void save_changes(std::string filename, std::vector<T> items)
	{
		std::ofstream file(filename, std::ios::trunc | std::ios::binary); // open for rewriting
		if (!file) throw FileException("Cannot open file for writing");

		file.write(reinterpret_cast<const char *>(&items[0]), sizeof(T)*items.size());
	}
};

struct FileException : public std::exception
{
	FileException(const char* msg) : std::exception(msg)
	{}
};