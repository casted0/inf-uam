### Assumes that the argument 'game' is an object with the following methods
# game.getMoves()
# game.makeMove(move)    changes lookahead player
# game.unmakeMove(move)  changes lookahead player
# game.changePlayer()    changes next turn player
# game.getUtility()
# game.isOver()
# game.__str__()

debug = False
inf = float('infinity')

def negamax(game, depthLeft, alpha, beta):
    if debug: print '   '*(10-depthLeft), game,
    # If at terminal state or depth limit, return utility value and move None
    if game.isOver() or depthLeft == 0:
        if debug: print 'terminal value',game.getUtility()
        return game.getUtility(), None
    if debug: print
    # Find best move and its value from current state
    bestValue = -inf
    bestMove = None
    for move in game.getMoves():
        # Apply a move to current state
        game.makeMove(move)
        # Use depth-first search to find eventual utility value and back it up.
        #  Negate it because it will come back in context of next player
        value = - negamax(game,depthLeft-1, -beta, -alpha)[0]
        # Remove the move from current state, to prepare for trying a different move
        game.unmakeMove(move)
        if debug: print '   '*(10-depthLeft), game, "move",move,"backed up value",value,
		
        if value > bestValue:
            # Value for this move is better than moves tried so far from this state.
            bestValue = value
	if bestValue >= beta:
		return bestValue, bestMove
	else: 
		alpha=max(alpha, bestValue)
            	bestMove = move
		if debug: print "new best"
		else:
		    if debug: print
		return bestValue, bestMove
