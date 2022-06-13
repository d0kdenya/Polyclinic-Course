function Node(id, name) {
    this.id = id
    this.name = name
    this.parent = null
    this.children = []
}

function Tree(id, name) {
    let node = new Node(id, name)
    this._root = node
}

Tree.prototype.traverseDF = function(callback) {

    (function recurse(currentNode) {
        for (let i = 0, length = currentNode.children.length; i < length; i++) {
            recurse(currentNode.children[i])
        }

        callback(currentNode)

    }) (this._root)

}

Tree.prototype.contains = function(callback, traversal) {
    traversal.call(this, callback)
}

Tree.prototype.add = function(id, name, toId, traversal) {
    let child = new Node(id, name),
        parent = null,
        callback = function(node) {
            if (node.name === toId) {
                parent = node
            }
        }

    this.contains(callback, traversal)

    if (parent) {
        parent.children.push(child)
        child.parent = parent
    } else {
        throw new Error('Cannot add node to a non-existent parent.')
    }
};

export default Tree