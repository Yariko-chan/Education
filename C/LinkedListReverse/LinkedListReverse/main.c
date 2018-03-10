#include <stdio.h>
#include <stdlib.h>

typedef struct Node {
	int i;
	struct Node * next;
} LLNode;

void print_linked_list(LLNode * first, int count);
LLNode * reverse_linked_list(first, size);
void add_node(LLNode ** old, LLNode ** new);

int main(void) {
	LLNode * first;
	first = malloc(sizeof(LLNode));
	int size = 0;

	for (int i = 1; i < 16; i++) {
		LLNode * new;
		new = malloc(sizeof(LLNode));
		new->i = i;
		add_node(&first, &new);
		size++;
	}

	print_linked_list(first, size);
	printf("\n");
	first = reverse_linked_list(first, size);
	print_linked_list(first, size);

	getchar();
}

void add_node(LLNode ** old, LLNode  * * new) {
	(*new)->next = *old;
	*old = *new;
}

void print_linked_list(LLNode * first, int count) {
	LLNode * n = first;
	for (int i = 0; i < count; i++) {
		printf("%d ", n->i);
		n = n->next;
	}
}

LLNode * reverse_linked_list(LLNode * first_node, int count) {
	LLNode * old_first;
	LLNode * first;
	LLNode * buf;

	old_first = first_node;
	first = old_first->next;

	for (int i = 0; i < count - 1; i++) {
		buf = first->next;
		first->next = old_first;
		old_first = first;
		first = buf;
	}
	return old_first;
}