#include <iostream>
#include <string>
#include <cstdio>  
#include <stdexcept> 
using namespace std;

// 10. Создать базовый класс список. 
// Реализовать на базе списка стек и очередь 
// с виртуальными функциями вставки и вытаскивания.

// base abstract class
class List {
private:
	int size;
public:
	int get_size() const;
	virtual void add(int i);
	virtual int get();
	virtual ~List() = 0; // just to prevent creating object List
};

int List::get_size() const
{
	return size;
}

void List::add(int i)
{
	size++;
}

int List::get()
{
	size--;
	return 0;
}

List::~List() {}

// fisrt-in-first-out
class Queue : public List {
private:
	// element
	struct Node {
		int value = 0;
		Node* next = nullptr; // pointer to next element
		Node* prev = nullptr; // pointer to prev element
	};
	Node * first_node;
	Node * last_node;
public:
	Queue();
	Queue(int init_val);
	Queue(const Queue &q);
	~Queue() override;
	void add(int i) override;
	int get() override;
};

Queue::Queue()
{
	first_node = nullptr;
	last_node = nullptr;
}

Queue::Queue(int init_val)
{
	first_node = nullptr;
	last_node = nullptr;
	add(init_val);
}

Queue::Queue(const Queue &q)
{
	first_node = nullptr;
	last_node = nullptr;
	Node* n = q.first_node;
	while (n != nullptr) {
		add(n->value);
		n = n->next;
	}
}

void Queue::add(int i)
{
	List::add(i);
	Node* node = new Node();
	node->value = i;
	// if first value in queue
	if (first_node == nullptr && last_node == nullptr) {
		first_node = node;
		last_node = node;
	}
	else {
		node->next = first_node;
		first_node->prev = node;
		first_node = node;
	}
}

int Queue::get()
{
	if (0 == get_size()) {
		throw out_of_range("Queue is void");
	}
	List::get();
	Node* old_last = last_node;
	int result = old_last->value;
	last_node = old_last->prev;
	if (nullptr != last_node) {
		last_node->next = nullptr;
	}
	delete old_last;
	return result;
}

Queue::~Queue()
{
	while (get_size() > 0) {
		get();
	}
}


// last-in-first-out
class Stack : public List {
private:
	// element
	struct Node {
		int value = 0;
		Node* next = nullptr; // pointer to next element
	};
	Node* last_node;
public:
	Stack();
	Stack(int init_val);
	Stack(const Stack &s);
	~Stack();
	void add(int i) override;
	int get() override;
};

Stack::Stack()
{
	last_node = nullptr;
}

Stack::Stack(int init_val)
{
	last_node = nullptr;
	add(init_val);
}

Stack::Stack(const Stack &s)
{
	last_node = nullptr;
	Node* n = s.last_node;
	// first create reversed stack from s
	Stack temp = Stack(); // reversed stack
	while (n != nullptr) {
		temp.add(n->value);
		n = n->next;
	}
	// reverse reversed stack to this stack
	n = temp.last_node;
	while (n != nullptr) {
		add(n->value);
		n = n->next;
	}
}

void Stack::add(int i)
{
	List::add(i);
	Node* node = new Node();
	node->value = i;
	node->next = last_node;
	last_node = node;
}

int Stack::get()
{
	if (0 == get_size()) {
		throw out_of_range("Stack is void");
	}
	List::get();
	Node* old_last = last_node;
	last_node = old_last->next;
	int result = old_last->value;
	delete old_last;
	return result;
}

Stack::~Stack()
{
	while (get_size() > 0) {
		get();
	}
}

// Testing
int main() {
	cout << "QUEUE\n";
	cout << "Enter three elements for queue (Enter for dividing):\n";

	Queue q = Queue();
	for (int i = 0; i < 3; i++) {
		int n;
		cin >> n;
		q.add(n);
	}

	cout << "The queue is: ";
	int size = q.get_size();
	for (int i = size - 1; i >= 0; i--) {
		cout << q.get() << " ";
	}

	cout << "\n-----------------------------------\n";
	cout << "STACK\n";
	cout << "Enter three elements for stack (Enter for dividing):\n";

	Stack s = Stack();
	for (int i = 0; i < 3; i++) {
		int n;
		cin >> n;
		s.add(n);
	}

	cout << "The stack is: ";
	size = s.get_size();
	for (int i = size - 1; i >= 0; i--) {
		cout << s.get() << " ";
	}


	getchar();
	getchar();
	return 0;
}