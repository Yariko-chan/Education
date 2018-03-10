#pragma once


/*
* Реализовать шаблон класса Stack. Реализовать пассивный итератор для
* класса Stack, в который будет передаваться определяемая пользователем функ-
* ция. Реализовать активный итератор для класса Stack, предоставляющий функ-
* ции продвижения по стеку и получения текущего элемента стека. Предоставить
* 3-4 функции для выполнения математических операций (найти среднее значе-
* ние элементов стека и т.п.), операции поиска по стеку, замены значения элемен-
* та стека на указанное, в которые как параметры передаются 2 активных итера-
* тора и необходимые добавочные данные.
*/

template <class T> class Iterator;
template <class T> class PassiveIterator;

// template class for stack
template <class T> class Stack {
private:
	friend class Iterator<T>;
	friend class PassiveIterator<T>;

	// element
	struct Node {
		T value;
		Node* next = nullptr; // pointer to next element
	};

	Node* last_node;
	int size;
public:

	// default constructor
	Stack()
	{
		last_node = nullptr;
	}

	// constructor with init value
	Stack(T init_val)
	{
		last_node = nullptr;
		push(init_val);
	}

	// copy constructor
	Stack(const Stack &s)
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

	// add element
	void push(T t)
	{
		size++;
		Node* node = new Node();
		node->value = t;
		node->next = last_node;
		last_node = node;
	}

	// get element
	T pop()
	{
		if (0 == get_size()) {
			throw out_of_range("Stack is void");
		}
		size--;
		Node* old_last = last_node;
		last_node = old_last->next;
		T result = old_last->value;
		delete old_last;
		return result;
	}

	// current size
	int get_size() const
	{
		return size;
	}


	T avg(Iterator<T> begin, Iterator<T> end) const
	{
		T sum = *begin;
		while (++begin != end) {
			sum += *begin;
		}
		return sum/size;
	}

	Iterator<T> max(Iterator<T> begin, Iterator<T> end) const
	{
		Iterator<T> max = begin;
		while (++begin != end) {
			max = (*begin > *max) ? begin : max;
		}
		return max;
	}

	Iterator<T> min(Iterator<T> begin, Iterator<T> end) const
	{
		Iterator<T> min = begin;
		while (++begin != end) {
			min = (*begin < *min) ? begin : min;
		}
		return min;
	}

	// return iterator pointing to node with value = val
	Iterator<T> search(Iterator<T> begin, Iterator<T> end, T val) const
	{
		while (begin != end) {
			if (*begin == val) {
				return begin;
			}
			else{
				++begin;
			}
		}
		return nullptr;
	}

	// replace value in node wuth val
	void replace(Iterator<T> node, T val)
	{
		node = val;
	}

	Iterator<T> begin() {
		return Iterator<T>(this);
	}

	Iterator<T> end() {
		return Iterator<T>();
	}

	// destructor
	~Stack()
	{
		while (get_size() > 0) {
			pop();
		}
	}
};

// active iterator
template <class T> class Iterator
{
	typename Stack<T>::Node* curr_node;

public:
	// default constructor
	Iterator(): curr_node(nullptr) {}

	// constructor with init value
	Iterator(Stack<T>* s):
		curr_node(s->last_node) {}

	// copy constructor
	Iterator(const Iterator &i):
		curr_node(i.curr_node) {}

	Iterator& operator++ () {
		curr_node = curr_node->next;
		return *this;
	}

	T& operator* () const {
		return curr_node->value;
	}

	// to replace value
	void operator=(T val) {
		curr_node->value = val;
	}

	bool operator==(const Iterator& that) const {
		return curr_node == that.curr_node;
	}

	bool operator!=(const Iterator& that) const {
		return curr_node != that.curr_node;
	}

	// destructor
	~Iterator() {
		// nothing to do
	}
};

template <class T> class PassiveIterator 
{
	Stack<T>* s;
	typename Stack<T>::Node* curr_node;
public:

	// constructor with init value
	PassiveIterator(Stack<T>* stack_p) :
		s(stack_p),
		curr_node(stack_p->last_node)
	{}

	void apply(void(*func) (const T&)) {
		while (curr_node != nullptr) {
			func(curr_node->value);
			curr_node = curr_node->next;
		}
	}

	~PassiveIterator() {}
};