#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include <ios>

using namespace std;

/*
* ¬ы €вл€етесь владельцем склада металлических изделий и нуждаетесь
* в инвентаризации, котора€ сказала бы вам, сколько всего различных изделий вы
* имеете, какое количество каждого из них у вас на руках и стоимость каждого из
* них. Ќапишите программу, котора€ бы создавала файл произвольного доступа,
* позвол€ла бы вводить данные по каждому изделию, давала бы вам возможность
* получать список всех изделий, удал€ть записи по издели€м, которых у вас уже
* нет, и позвол€ла бы обновл€ть любую информацию в файле.  лючом должен
* быть идентификационный номер издели€.
*/

#define FILENAME "Items"

struct Item {
	int id;
	char name[100];
	int count;
	float price;

	Item() :
		id(-1),
		count(0),
		price(0)
	{
		name[0] = '\0';
	}

	Item(int i, const char* n, int c, float p) :
		id(i),
		count(c),
		price(p)
	{
		strncpy_s(name, sizeof(name) - 1, n, sizeof(name) - 1);
	}

	bool is_empty() {
		return id == -1;
	}
};

std::ostream& operator<<(std::ostream& stream, const Item& item)
{
	stream << item.id << " " << item.name << " " << item.count << " " << item.price << '\n';
	return stream;
}


struct FileException : public std::exception
{
	FileException(const char* msg) : std::exception(msg)
	{}
};

// add
void add(const Item& item)
{
	std::ofstream file(FILENAME, std::ios::app | std::ios::binary); // open for adding to the end
	if (!file) throw FileException("Cannot open file for writing");

	file.write(reinterpret_cast<const char *>(&item), sizeof(item));
}

// save
// get_all
std::vector<Item> get_all()
{
	std::ifstream file(FILENAME, std::ios::binary | std::ios::in);
	if (!file) throw FileException("Cannot open file for reading");

	file.seekg(0, std::ios::end);
	auto file_size = (size_t)file.tellg();
	file.seekg(0, std::ios::beg);

	std::vector<Item> items;
	if (file_size == 0)
		return items;

	items.resize(file_size / sizeof(Item));
	file.read(reinterpret_cast<char *>(&items[0]), file_size);
	return items;
}

bool seek_by_id(int id, std::fstream& file) {

	Item item;
	int item_index = 0;
	while (!file.eof()) {

		file.read(reinterpret_cast<char *>(&item), sizeof(Item));
		if (item.id == id) {
			file.seekg(-(std::streamoff)sizeof(Item), std::ios::cur);
			break;
		}
		item_index++;

	}
	return item.id == id;
}

// get_by_id
Item get_by_id(int id) {
	std::fstream file(FILENAME, std::ios::binary | std::ios::in);
	if (!file) throw FileException("Cannot open file for writing");

	Item item;
	if (seek_by_id(id, file)) {
		file.read(reinterpret_cast<char *>(&item), sizeof(Item));
	}
	return item;
}

// update_by_id
void update_by_id(Item item) {
	std::fstream file(FILENAME, std::ios::binary | std::ios::in | std::ios::out);
	if (!file) throw FileException("Cannot open file");

	if (seek_by_id(item.id, file)) {
		file.write(reinterpret_cast<const char *>(&item), sizeof(item));
	}
}

// delete 
void delete_by_id(int id) {
	std::fstream file(FILENAME, std::ios::binary | std::ios::in | std::ios::out);
	if (!file) throw FileException("Cannot open file");

	std::vector<Item> items = get_all();
	bool needRewrite = false; // if such item not found, no need to rewrite
	for (size_t i = 0; i < items.size(); i++) {
		if (items[i].id == id) {
			items.erase(items.begin() + i);
			needRewrite = true;
			break;
		}
	}
	if (!needRewrite) return;

	// rewrite
	file.close();
	file.open(FILENAME, std::ios::binary | std::ios::out | std::ios::trunc);
	if (!file) throw FileException("Cannot open file");
	if (items.size() == 0) return;
	file.write(reinterpret_cast<const char *>(&items[0]), sizeof(Item)*items.size());
}

// test
int main()
{
	int ch;
	do {

		cout << "\n\nITEMS" << "\n";

		// show existing items
		try
		{
			vector<Item> items = get_all();
			if (items.size() == 0) {
				cout << "No data saved for now \n";
			}
			else {
				for (size_t i = 0; i < items.size(); i++) {
					cout << items[i];
				}
			}
		}
		catch (const FileException& ex)
		{
			cout << "No data saved for now \n";
		}

		// show menu
		cout << "\n\n"
			<< "Select operation:\n"
			<< "1. Add new                         2. Delete by id\n"
			<< "3. Update existing item by id      0. Exit\n";
		cin >> ch;
		switch (ch) {
		case 1:
		{
			int id, count;
			string name;
			float price;
			cout << "Id: ";
			cin >> id;
			cout << "Name: ";
			cin >> name;
			cout << "Count: ";
			cin >> count;
			cout << "Price: ";
			cin >> price;
			Item item(id, name.c_str(), count, price);
			add(item);
		}
			break;
		case 2:
		{
			cout << "Enter id for delete: ";
			int id;
			cin >> id;
			delete_by_id(id);
			cout << "Deleted\n";
		}
			break;
		case 3:
		{
			int id, count;
			string name;
			float price;
			cout << "Enter id for update: ";
			cin >> id;
			cout << "New name: ";
			cin >> name;
			cout << "Count: ";
			cin >> count;
			cout << "Price: ";
			cin >> price;
			Item item(id, name.c_str(), count, price);

			update_by_id(item);
			cout << "Updated\n";
		}
			break;
		default:
			break;
		}

	} while (ch != 0);
}